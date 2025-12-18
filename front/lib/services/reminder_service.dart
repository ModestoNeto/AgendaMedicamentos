import '../api/api_client.dart';

class ReminderService {
  final ApiClient _api = ApiClient();

  Future<Map<String, dynamic>> createReminder({
    required int medicationId,
    required int userId,
    required String datetimeIso,
  }) async {
    final res = await _api.post('/reminders', {
      'medicationId': medicationId,
      'userId': userId,
      'datetime': datetimeIso,
      'status': 'PENDING',
    });
    return (res as Map).cast<String, dynamic>();
  }

  Future<List<Map<String, dynamic>>> listReminders() async {
    final res = await _api.get('/reminders');
    final list = (res as List?) ?? [];
    return list.map((e) => (e as Map).cast<String, dynamic>()).toList();
  }

  Future<List<Map<String, dynamic>>> listByMedication(int medicationId) async {
    final res = await _api.get('/reminders/medication/$medicationId');
    final list = (res as List?) ?? [];
    return list.map((e) => (e as Map).cast<String, dynamic>()).toList();
  }

  Future<Map<String, dynamic>> updateReminder(
    int id, {
    String? datetimeIso,
    String? status,
  }) async {
    final body = <String, dynamic>{};
    if (datetimeIso != null) body['datetime'] = datetimeIso;
    if (status != null) body['status'] = status;

    final res = await _api.put('/reminders/$id', body);
    return (res as Map).cast<String, dynamic>();
  }

  Future<void> deleteReminder(int id) => _api.delete('/reminders/$id');
}
