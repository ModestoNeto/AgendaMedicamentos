import 'package:flutter/material.dart';
import '../app_session.dart';
import '../services/medication_service.dart';
import '../services/reminder_service.dart';

class AddMedicationScreen extends StatefulWidget {
  const AddMedicationScreen({super.key});

  @override
  State<AddMedicationScreen> createState() => _AddMedicationScreenState();
}

class _AddMedicationScreenState extends State<AddMedicationScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _doseController = TextEditingController();
  final _frequencyController = TextEditingController(text: '1x ao dia');

  TimeOfDay? _time;
  bool _isLoading = false;

  final _medService = MedicationService();
  final _remService = ReminderService();

  @override
  void dispose() {
    _nameController.dispose();
    _doseController.dispose();
    _frequencyController.dispose();
    super.dispose();
  }

  Future<void> _pickTime() async {
    final picked = await showTimePicker(
      context: context,
      initialTime: _time ?? TimeOfDay.now(),
    );
    if (picked != null) setState(() => _time = picked);
  }

  Future<void> _save() async {
    final ok = _formKey.currentState?.validate() ?? false;
    if (!ok) return;

    final userId = AppSession.userId;
    if (userId == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Faça login/cadastro antes.')),
      );
      return;
    }

    if (_time == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Selecione um horário.')),
      );
      return;
    }

    setState(() => _isLoading = true);

    try {
      final medication = await _medService.createMedication(
        userId: userId,
        name: _nameController.text.trim(),
        dose: _doseController.text.trim(),
        frequency: _frequencyController.text.trim(),
      );

      final medIdRaw = medication['id'];
      if (medIdRaw is! int) {
        throw Exception('API não retornou id do medicamento.');
      }

      final now = DateTime.now();
      final dt = DateTime(now.year, now.month, now.day, _time!.hour, _time!.minute);
      final iso = dt.toIso8601String();

      await _remService.createReminder(
        medicationId: medIdRaw,
        userId: userId,
        datetimeIso: iso,
      );

      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Medicamento cadastrado!')),
      );
      Navigator.pop(context, true);
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Erro: $e')),
      );
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final timeLabel = _time == null ? 'Selecionar horário' : _time!.format(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Adicionar Medicamento')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: AbsorbPointer(
            absorbing: _isLoading,
            child: Column(
              children: [
                TextFormField(
                  controller: _nameController,
                  decoration: const InputDecoration(labelText: 'Nome do medicamento'),
                  validator: (v) => (v == null || v.trim().isEmpty) ? 'Informe o nome' : null,
                ),
                const SizedBox(height: 12),
                TextFormField(
                  controller: _doseController,
                  decoration: const InputDecoration(labelText: 'Dosagem (ex: 1 comprimido)'),
                  validator: (v) => (v == null || v.trim().isEmpty) ? 'Informe a dosagem' : null,
                ),
                const SizedBox(height: 12),
                TextFormField(
                  controller: _frequencyController,
                  decoration: const InputDecoration(labelText: 'Frequência (ex: 1x ao dia)'),
                  validator: (v) => (v == null || v.trim().isEmpty) ? 'Informe a frequência' : null,
                ),
                const SizedBox(height: 12),
                ListTile(
                  contentPadding: EdgeInsets.zero,
                  leading: const Icon(Icons.access_time),
                  title: Text(timeLabel),
                  onTap: _pickTime,
                ),
                const SizedBox(height: 24),
                SizedBox(
                  width: double.infinity,
                  height: 48,
                  child: ElevatedButton(
                    onPressed: _isLoading ? null : _save,
                    child: _isLoading
                        ? const SizedBox(
                            width: 22,
                            height: 22,
                            child: CircularProgressIndicator(strokeWidth: 2),
                          )
                        : const Text('Salvar'),
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
