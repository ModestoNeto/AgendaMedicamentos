import 'package:flutter/material.dart';

class MedicationHistory {
  final String name;
  final String dosage;
  final String time;
  final bool isActive;
  final String? removedDate;

  MedicationHistory({
    required this.name,
    required this.dosage,
    required this.time,
    required this.isActive,
    this.removedDate,
  });
}

class MedicationHistoryScreen extends StatelessWidget {
  const MedicationHistoryScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final List<MedicationHistory> activeMedications = [
      MedicationHistory(
        name: 'Paracetamol',
        dosage: '500mg',
        time: '08:00',
        isActive: true,
      ),
      MedicationHistory(
        name: 'Ibuprofeno',
        dosage: '400mg',
        time: '14:00',
        isActive: true,
      ),
    ];

    final List<MedicationHistory> removedMedications = [
      MedicationHistory(
        name: 'pica',
        dosage: '20cm',
        time: '14:30',
        isActive: false,
        removedDate: '04/12/2025',
      ),
    ];

    return Scaffold(
      backgroundColor: const Color(0xFFF5F5F5),
      body: SafeArea(
        child: Column(
          children: [
            // Header
            Container(
              color: Colors.white,
              padding: const EdgeInsets.all(16.0),
              child: Row(
                children: [
                  IconButton(
                    onPressed: () => Navigator.pop(context),
                    icon: const Icon(Icons.arrow_back),
                    padding: EdgeInsets.zero,
                    constraints: const BoxConstraints(),
                  ),
                  const SizedBox(width: 16),
                  const Text(
                    'Voltar',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ],
              ),
            ),
            
            // Título
            Container(
              width: double.infinity,
              color: Colors.white,
              padding: const EdgeInsets.fromLTRB(24, 0, 24, 24),
              child: const Text(
                'Histórico Completo',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  color: Colors.black87,
                ),
              ),
            ),
            
            Expanded(
              child: SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.all(24.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Card(
                        elevation: 2,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(20.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'Medicamentos em Uso (${activeMedications.length})',
                                style: const TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.w600,
                                  color: Colors.black87,
                                ),
                              ),
                              const SizedBox(height: 16),
                              ...activeMedications.map((medication) {
                                return _MedicationHistoryCard(
                                  medication: medication,
                                );
                              }).toList(),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 20),
                      
                      Card(
                        elevation: 2,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(20.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'Medicamentos Removidos (${removedMedications.length})',
                                style: const TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.w600,
                                  color: Colors.black87,
                                ),
                              ),
                              const SizedBox(height: 16),
                              ...removedMedications.map((medication) {
                                return _MedicationHistoryCard(
                                  medication: medication,
                                );
                              }).toList(),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _MedicationHistoryCard extends StatelessWidget {
  final MedicationHistory medication;

  const _MedicationHistoryCard({
    required this.medication,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.grey[50],
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: Colors.grey[200]!),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Text(
                medication.name,
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.black87,
                ),
              ),
              const SizedBox(width: 8),
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                decoration: BoxDecoration(
                  color: medication.isActive
                      ? const Color(0xFF4CAF50)
                      : const Color(0xFF1565C0),
                  borderRadius: BorderRadius.circular(4),
                ),
                child: Text(
                  medication.isActive ? 'Ativo' : 'Removido',
                  style: const TextStyle(
                    fontSize: 11,
                    color: Colors.white,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(height: 6),
          Text(
            'Dosagem: ${medication.dosage}',
            style: TextStyle(
              fontSize: 13,
              color: Colors.grey[700],
            ),
          ),
          const SizedBox(height: 4),
          Row(
            children: [
              Icon(Icons.access_time, size: 14, color: Colors.grey[600]),
              const SizedBox(width: 4),
              Text(
                medication.time,
                style: TextStyle(
                  fontSize: 13,
                  color: Colors.grey[600],
                ),
              ),
            ],
          ),
          if (!medication.isActive && medication.removedDate != null) ...[
            const SizedBox(height: 6),
            Text(
              'Removido em: ${medication.removedDate}',
              style: TextStyle(
                fontSize: 12,
                color: Colors.grey[500],
                fontStyle: FontStyle.italic,
              ),
            ),
          ],
        ],
      ),
    );
  }
}
