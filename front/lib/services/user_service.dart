import '../api/api_client.dart';

class UserService {
  final ApiClient _api = ApiClient();

  Future<void> createUser({
    required String name,
    required String email,
    required String password,
  }) {
    return _api.post('/users', {
      'name': name,
      'email': email,
      'password': password,
    });
  }

  Future<List<dynamic>> listUsers() async {
    final res = await _api.get('/users');
    return res as List? ?? [];
  }
}
