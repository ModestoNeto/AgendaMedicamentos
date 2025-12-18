import 'package:flutter/material.dart';

import 'screens/login_screen.dart';
import 'screens/login_form_screen.dart';
import 'screens/register_screen.dart';
import 'screens/home_screen.dart';
import 'screens/current_medications_screen.dart';
import 'screens/add_medication_screen.dart';
import 'screens/medication_reminder_screen.dart';
import 'screens/medication_history_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MediCare',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF2196F3)),
        useMaterial3: true,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const LoginScreen(),
        '/login-form': (context) => const LoginFormScreen(),
        '/register': (context) => const RegisterScreen(),
        '/home': (context) => const HomeScreen(),
        '/current-medications': (context) => const CurrentMedicationsScreen(),
        '/add-medication': (context) => const AddMedicationScreen(),
        '/medication-history': (context) => const MedicationHistoryScreen(),
      },

      // ✅ Rota do lembrete agora é dinâmica via arguments
      onGenerateRoute: (settings) {
        if (settings.name == '/medication-reminder') {
          final args = settings.arguments;

          if (args is Map<String, dynamic>) {
            final medicationName = args['medicationName'] as String? ?? 'Medicamento';
            final reminderId = args['reminderId'] as int?;
            final scheduledAt = args['scheduledAt'] as DateTime?;

            if (reminderId != null && scheduledAt != null) {
              return MaterialPageRoute(
                builder: (_) => MedicationReminderScreen(
                  medicationName: medicationName,
                  reminderId: reminderId,
                  scheduledAt: scheduledAt,
                ),
              );
            }
          }

          // fallback se chamarem a rota sem argumentos
          return MaterialPageRoute(
            builder: (_) => const Scaffold(
              body: Center(
                child: Text('Erro: lembrete sem parâmetros.'),
              ),
            ),
          );
        }

        return null;
      },
    );
  }
}
