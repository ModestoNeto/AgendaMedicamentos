import 'package:flutter/material.dart';
import '../app_session.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final name = AppSession.userName ?? 'Usuário';
    return Scaffold(
      appBar: AppBar(
        title: const Text('Agenda de Medicamentos'),
        actions: [
          IconButton(
            tooltip: 'Sair',
            onPressed: () {
              AppSession.clear();
              Navigator.pushReplacementNamed(context, '/login');
            },
            icon: const Icon(Icons.logout),
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Align(
              alignment: Alignment.centerLeft,
              child: Text('Olá, $name',
                  style: const TextStyle(fontSize: 20, fontWeight: FontWeight.w700)),
            ),
            const SizedBox(height: 16),
            ListTile(
              leading: const Icon(Icons.medication),
              title: const Text('Medicamentos atuais'),
              onTap: () => Navigator.pushNamed(context, '/current-medications'),
            ),
            ListTile(
              leading: const Icon(Icons.add),
              title: const Text('Adicionar medicamento'),
              onTap: () => Navigator.pushNamed(context, '/add-medication'),
            ),
            ListTile(
              leading: const Icon(Icons.history),
              title: const Text('Histórico'),
              onTap: () => Navigator.pushNamed(context, '/history'),
            ),
          ],
        ),
      ),
    );
  }
}
