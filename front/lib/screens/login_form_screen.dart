import 'package:flutter/material.dart';
import '../app_session.dart';
import '../services/user_service.dart';

class LoginFormScreen extends StatefulWidget {
  const LoginFormScreen({super.key});

  @override
  State<LoginFormScreen> createState() => _LoginFormScreenState();
}

class _LoginFormScreenState extends State<LoginFormScreen> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();

  bool _isLoading = false;
  final _userService = UserService();

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    final ok = _formKey.currentState?.validate() ?? false;
    if (!ok) return;

    setState(() => _isLoading = true);

    try {
      final users = await _userService.listUsers();
      final email = _emailController.text.trim().toLowerCase();

      final found = users.where((u) => (u['email']?.toString().toLowerCase() == email)).toList();
      if (found.isEmpty) {
        throw Exception('Usuário não encontrado. Cadastre-se.');
      }

      // Sem auth real, salva só o básico
      final user = found.first;
      final id = user['id'];
      if (id is int) AppSession.userId = id;
      AppSession.userName = user['name']?.toString();
      AppSession.userEmail = user['email']?.toString();

      if (!mounted) return;
      Navigator.pushReplacementNamed(context, '/home');
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Erro no login: $e')));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFE8EAF6),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24),
            child: Card(
              elevation: 8,
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
              child: Container(
                constraints: const BoxConstraints(maxWidth: 500),
                padding: const EdgeInsets.all(40),
                child: Form(
                  key: _formKey,
                  child: AbsorbPointer(
                    absorbing: _isLoading,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text('Entrar',
                            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
                        const SizedBox(height: 24),

                        const Text('E-mail',
                            style: TextStyle(fontSize: 14, fontWeight: FontWeight.w500)),
                        const SizedBox(height: 8),
                        TextFormField(
                          controller: _emailController,
                          keyboardType: TextInputType.emailAddress,
                          textInputAction: TextInputAction.next,
                          decoration: _inputDecoration('seu@email.com'),
                          validator: (v) {
                            final s = v?.trim() ?? '';
                            if (s.isEmpty) return 'Informe seu e-mail';
                            final ok = RegExp(r'^[^@]+@[^@]+\.[^@]+').hasMatch(s);
                            if (!ok) return 'E-mail inválido';
                            return null;
                          },
                        ),
                        const SizedBox(height: 16),

                        const Text('Senha',
                            style: TextStyle(fontSize: 14, fontWeight: FontWeight.w500)),
                        const SizedBox(height: 8),
                        TextFormField(
                          controller: _passwordController,
                          obscureText: true,
                          textInputAction: TextInputAction.done,
                          onFieldSubmitted: (_) => _login(),
                          decoration: _inputDecoration('••••••••'),
                          validator: (v) {
                            final s = v ?? '';
                            if (s.isEmpty) return 'Informe sua senha';
                            if (s.length < 6) return 'Mínimo 6 caracteres';
                            return null;
                          },
                        ),
                        const SizedBox(height: 24),

                        SizedBox(
                          width: double.infinity,
                          height: 50,
                          child: ElevatedButton(
                            onPressed: _isLoading ? null : _login,
                            style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.black,
                              foregroundColor: Colors.white,
                              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                              elevation: 0,
                            ),
                            child: _isLoading
                                ? const SizedBox(
                                    width: 22,
                                    height: 22,
                                    child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white),
                                  )
                                : const Text('Entrar',
                                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600)),
                          ),
                        ),

                        const SizedBox(height: 16),
                        TextButton(
                          onPressed: _isLoading ? null : () => Navigator.pushNamed(context, '/register'),
                          child: const Text('Não tem conta? Cadastre-se'),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  InputDecoration _inputDecoration(String hint) => InputDecoration(
        hintText: hint,
        hintStyle: TextStyle(color: Colors.grey[400]),
        filled: true,
        fillColor: const Color(0xFFF5F5F5),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(8),
          borderSide: BorderSide.none,
        ),
        contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 16),
      );
}
