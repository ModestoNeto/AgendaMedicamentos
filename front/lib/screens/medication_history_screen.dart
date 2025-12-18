import 'package:flutter/material.dart';
import '../app_session.dart';
import '../services/reminder_service.dart';

class MedicationHistoryScreen extends StatefulWidget {
  const MedicationHistoryScreen({super.key});

  @override
  State<MedicationHistoryScreen> createState() => _MedicationHistoryScreenState();
}

class _MedicationHistoryScreenState extends State<MedicationHistoryScreen> {
  final _remService = ReminderService();
  late Future<List<Map<String, dynamic>>> _future;

  @override
  void initState() {
    super.initState();
    _future = _load();
  }

  Future<List<Map<String, dynamic>>> _load() async {
    final userId = AppSession.userId;
    if (userId == null) return [];
    final list = await _remService.listReminders();
    return list.where((r) => r['userId'] == userId || r['user']?['id'] == userId).toList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Histórico')),
      body: FutureBuilder<List<Map<String, dynamic>>>(
        future: _future,
        builder: (context, snap) {
          if (snap.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snap.hasError) return Center(child: Text('Erro: ${snap.error}'));

          final data = snap.data ?? [];
          if (AppSession.userId == null) {
            return const Center(child: Text('Faça login/cadastro para ver o histórico.'));
          }
          if (data.isEmpty) return const Center(child: Text('Sem histórico ainda.'));

          data.sort((a, b) {
            final da = DateTime.tryParse(a['datetime']?.toString() ?? '') ?? DateTime(1970);
            final db = DateTime.tryParse(b['datetime']?.toString() ?? '') ?? DateTime(1970);
            return db.compareTo(da);
          });

          return RefreshIndicator(
            onRefresh: () async {
              setState(() => _future = _load());
              await _future;
            },
            child: ListView.separated(
              padding: const EdgeInsets.all(16),
              itemCount: data.length,
              separatorBuilder: (_, __) => const SizedBox(height: 12),
              itemBuilder: (context, i) {
                final r = data[i];
                final dt = DateTime.tryParse(r['datetime']?.toString() ?? '');
                final status = r['status']?.toString() ?? '-';
                final title = r['medication']?['name']?.toString() ?? 'Lembrete';

                return Card(
                  child: ListTile(
                    title: Text(title),
                    subtitle: Text([
                      if (dt != null) '${dt.day.toString().padLeft(2, '0')}/${dt.month.toString().padLeft(2, '0')} ${TimeOfDay.fromDateTime(dt).format(context)}',
                      'Status: $status',
                    ].join(' • ')),
                  ),
                );
              },
            ),
          );
        },
      ),
    );
  }
}
