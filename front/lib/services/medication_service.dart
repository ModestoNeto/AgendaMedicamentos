import '../api/api_client.dart';

class MedicationService {
  final ApiClient _api = ApiClient();

  Future<Map<String, dynamic>> createMedication({
    required int userId,
    required String name,
    required String dose,
    required String frequency,
  }) async {
    final res = await _api.post('/medications', {
      'userId': userId,
      'name': name,
      'dose': dose,
      'frequency': frequency,
    });
    return (res as Map).cast<String, dynamic>();
  }

  Future<List<Map<String, dynamic>>> listMedications() async {
    final res = await _api.get('/medications');
    final list = (res as List?) ?? [];
    return list.map((e) => (e as Map).cast<String, dynamic>()).toList();
  }

  Future<void> deleteMedication(int id) => _api.delete('/medications/$id');
}
