import 'package:flutter/material.dart';
import '../services/reminder_service.dart';

class MedicationReminderScreen extends StatefulWidget {
  final String medicationName;
  final DateTime scheduledAt;
  final int reminderId;

  const MedicationReminderScreen({
    super.key,
    required this.medicationName,
    required this.scheduledAt,
    required this.reminderId,
  });

  @override
  State<MedicationReminderScreen> createState() => _MedicationReminderScreenState();
}

class _MedicationReminderScreenState extends State<MedicationReminderScreen> {
  final _remService = ReminderService();
  bool _isLoading = false;

  Future<void> _markTaken() async {
    setState(() => _isLoading = true);
    try {
      await _remService.updateReminder(widget.reminderId, status: 'TAKEN');
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Marcado como tomado!')),
      );
      Navigator.pop(context, true);
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Erro: $e')));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _snooze() async {
    setState(() => _isLoading = true);
    try {
      final newDt = DateTime.now().add(const Duration(minutes: 15));
      await _remService.updateReminder(
        widget.reminderId,
        datetimeIso: newDt.toIso8601String(),
        status: 'PENDING',
      );

      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Adiado em 15 minutos.')),
      );
      Navigator.pop(context, true);
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Erro: $e')));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final time = TimeOfDay.fromDateTime(widget.scheduledAt).format(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Lembrete')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: AbsorbPointer(
          absorbing: _isLoading,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(widget.medicationName,
                  style: const TextStyle(fontSize: 22, fontWeight: FontWeight.w700)),
              const SizedBox(height: 8),
              Text('Horário: $time', style: const TextStyle(fontSize: 16)),
              const SizedBox(height: 24),

              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton.icon(
                  onPressed: _isLoading ? null : _markTaken,
                  icon: _isLoading
                      ? const SizedBox(
                          width: 18,
                          height: 18,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      : const Icon(Icons.check),
                  label: const Text('Tomei o medicamento'),
                ),
              ),
              const SizedBox(height: 12),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: OutlinedButton.icon(
                  onPressed: _isLoading ? null : _snooze,
                  icon: const Icon(Icons.snooze),
                  label: const Text('Adiar 15 min'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
