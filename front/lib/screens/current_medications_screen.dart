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

  Future<void> _load() async {
    final userId = AppSession.userId;
    if (userId == null) {
      _meds = [];
      _reminders = [];
      return;
    }

    final meds = await _medService.listMedications(); // List<Map<String,dynamic>>
    final reminders = await _remService.listReminders();

    // filtra por user se existir userId no payload
    _meds = meds.where((m) => m['userId'] == userId || m['user']?['id'] == userId).toList();
    _reminders =
        reminders.where((r) => r['userId'] == userId || r['user']?['id'] == userId).toList();
  }

  Map<String, dynamic>? _nextReminderForMed(int medId) {
    final list = _reminders
        .where((r) => r['medicationId'] == medId || r['medication']?['id'] == medId)
        .toList();

    if (list.isEmpty) return null;

    list.sort((a, b) {
      final da = DateTime.tryParse(a['datetime']?.toString() ?? '') ?? DateTime(2100);
      final db = DateTime.tryParse(b['datetime']?.toString() ?? '') ?? DateTime(2100);
      return da.compareTo(db);
    });

    return list.first;
  }

  Future<void> _openReminder(Map<String, dynamic> med) async {
    final medId = med['id'];
    if (medId is! int) return;

    final reminder = _nextReminderForMed(medId);
    if (reminder == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Sem lembretes para este medicamento.')),
      );
      return;
    }

    final reminderId = reminder['id'];
    final dt = DateTime.tryParse(reminder['datetime']?.toString() ?? '');

    if (reminderId is! int || dt == null) return;

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Medicamentos Atuais')),
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
                final medId = med['id'] as int?;
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
    );
  }
}
