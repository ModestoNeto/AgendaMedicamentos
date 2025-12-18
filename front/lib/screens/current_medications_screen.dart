import 'package:flutter/material.dart';
import '../app_session.dart';
import '../services/medication_service.dart';
import '../services/reminder_service.dart';
import 'medication_reminder_screen.dart';

class CurrentMedicationsScreen extends StatefulWidget {
  const CurrentMedicationsScreen({super.key});

  @override
  State<CurrentMedicationsScreen> createState() => _CurrentMedicationsScreenState();
}

class _CurrentMedicationsScreenState extends State<CurrentMedicationsScreen> {
  final _medService = MedicationService();
  final _remService = ReminderService();

  late Future<void> _loader;

  List<Map<String, dynamic>> _meds = [];
  List<Map<String, dynamic>> _reminders = [];

  @override
  void initState() {
    super.initState();
    _loader = _load();
  }

  int? _asInt(dynamic v) {
    if (v is int) return v;
    if (v is num) return v.toInt();
    if (v is String) return int.tryParse(v);
    return null;
  }

  int? _extractUserId(Map<String, dynamic> m) {
    final v = m['userId'] ?? m['user_id'] ?? m['user']?['id'];
    return _asInt(v);
  }

  int? _extractMedicationId(Map<String, dynamic> r) {
    final v = r['medicationId'] ?? r['medication_id'] ?? r['medication']?['id'];
    return _asInt(v);
  }

  Future<void> _load() async {
    final userId = AppSession.userId;
    if (userId == null) {
      _meds = [];
      _reminders = [];
      return;
    }

    final meds = await _medService.listMedications();
    final reminders = await _remService.listReminders();

    _meds = meds.where((m) => _extractUserId(m) == userId).toList();
    _reminders = reminders.where((r) => _extractUserId(r) == userId).toList();
  }

  Map<String, dynamic>? _nextReminderForMed(int medId) {
    final list = _reminders.where((r) => _extractMedicationId(r) == medId).toList();
    if (list.isEmpty) return null;

    list.sort((a, b) {
      final da = DateTime.tryParse(a['datetime']?.toString() ?? '') ?? DateTime(2100);
      final db = DateTime.tryParse(b['datetime']?.toString() ?? '') ?? DateTime(2100);
      return da.compareTo(db);
    });

    return list.first;
  }

  Future<void> _openReminder(Map<String, dynamic> med) async {
    final medId = _asInt(med['id']);
    if (medId == null) return;

    final reminder = _nextReminderForMed(medId);
    if (reminder == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Sem lembretes para este medicamento.')),
      );
      return;
    }

    final reminderId = _asInt(reminder['id']);
    final dt = DateTime.tryParse(reminder['datetime']?.toString() ?? '');

    if (reminderId == null || dt == null) return;

    final changed = await Navigator.push<bool>(
      context,
      MaterialPageRoute(
        builder: (_) => MedicationReminderScreen(
          medicationName: med['name']?.toString() ?? 'Medicamento',
          scheduledAt: dt,
          reminderId: reminderId,
        ),
      ),
    );

    if (changed == true) {
      setState(() => _loader = _load());
    }
  }

  Future<void> _deleteMed(int medId) async {
    try {
      await _medService.deleteMedication(medId);
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Removido.')));
      setState(() => _loader = _load());
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Erro: $e')));
    }
  }

  Future<void> _goAddMedication() async {
    final changed = await Navigator.pushNamed(context, '/add-medication');
    if (changed == true) {
      setState(() => _loader = _load());
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Medicamentos Atuais'),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: _goAddMedication,
          ),
        ],
      ),
      body: FutureBuilder<void>(
        future: _loader,
        builder: (context, snap) {
          if (snap.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snap.hasError) {
            return Center(child: Text('Erro: ${snap.error}'));
          }

          if (AppSession.userId == null) {
            return const Center(child: Text('Faça login/cadastro para ver seus medicamentos.'));
          }

          if (_meds.isEmpty) {
            return const Center(child: Text('Nenhum medicamento cadastrado.'));
          }

          return RefreshIndicator(
            onRefresh: () async {
              setState(() => _loader = _load());
              await _loader;
            },
            child: ListView.separated(
              padding: const EdgeInsets.all(16),
              itemCount: _meds.length,
              separatorBuilder: (_, __) => const SizedBox(height: 12),
              itemBuilder: (context, i) {
                final med = _meds[i];
                final medId = _asInt(med['id']);
                final next = medId == null ? null : _nextReminderForMed(medId);

                final dt = next == null ? null : DateTime.tryParse(next['datetime']?.toString() ?? '');
                final status = next?['status']?.toString();

                return Card(
                  child: ListTile(
                    title: Text(med['name']?.toString() ?? 'Medicamento'),
                    subtitle: Text([
                      if (med['dose'] != null) 'Dose: ${med['dose']}',
                      if (dt != null) 'Horário: ${TimeOfDay.fromDateTime(dt).format(context)}',
                      if (status != null) 'Status: $status',
                    ].join(' • ')),
                    onTap: () => _openReminder(med),
                    trailing: medId == null
                        ? null
                        : IconButton(
                            icon: const Icon(Icons.delete),
                            onPressed: () => _deleteMed(medId),
                          ),
                  ),
                );
              },
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _goAddMedication,
        child: const Icon(Icons.add),
      ),
    );
  }
}
