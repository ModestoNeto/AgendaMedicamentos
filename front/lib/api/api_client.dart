import 'dart:convert';
import 'package:http/http.dart' as http;
import 'api_config.dart';

class ApiClient {
  final _client = http.Client();

  Map<String, String> get _headers => {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      };

  Uri _uri(String path) {
    return Uri.parse('${ApiConfig.baseUrl}$path');
  }

  Future<dynamic> get(String path) async {
    final res = await _client.get(_uri(path), headers: _headers);
    _validate(res);
    return res.body.isEmpty ? null : jsonDecode(res.body);
  }

  Future<dynamic> post(String path, Map<String, dynamic> body) async {
    final res = await _client.post(
      _uri(path),
      headers: _headers,
      body: jsonEncode(body),
    );
    _validate(res);
    return res.body.isEmpty ? null : jsonDecode(res.body);
  }

  Future<dynamic> put(String path, Map<String, dynamic> body) async {
    final res = await _client.put(
      _uri(path),
      headers: _headers,
      body: jsonEncode(body),
    );
    _validate(res);
    return res.body.isEmpty ? null : jsonDecode(res.body);
  }

  Future<void> delete(String path) async {
    final res = await _client.delete(_uri(path), headers: _headers);
    _validate(res);
  }

  void _validate(http.Response res) {
    if (res.statusCode < 200 || res.statusCode >= 300) {
      throw Exception('Erro ${res.statusCode}: ${res.body}');
    }
  }
}
