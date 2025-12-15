import 'package:flutter/material.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Feedback App',
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 20, 20, 20),
      ),
      home: const FeedbackScreen(),
    );
  }
}

class FeedbackScreen extends StatelessWidget {
  const FeedbackScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Leave Feedback")),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const TextField(
              maxLines: 4,
              decoration: InputDecoration(
                hintText: "Write your feedback here...",
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                // Later: send to server / email / firebase
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("Feedback submitted")),
                );
              },
              child: const Text("Submit"),
            ),
          ],
        ),
      ),
    );
  }
}
