# password-manager-assignment
## Overview
This Password Manager App is developed using Jetpack Compose and Kotlin. It allows users to securely store, manage, and retrieve their passwords. The app includes features such as adding, editing, and deleting passwords, biometric authentication for security, and encryption/decryption of stored passwords.

## Features
 - **Add Passwords**: Users can add new password entries.
 - **Edit Passwords**: Users can update/edit existing password entries.
 - **Delete Passwords**: Users can delete password entries.
 - **Biometric Authentication**: Users can authenticate using biometric authentication.
 - **Encryption/Decryption**: Passwords are stored in an encrypted format to ensure security.

## Prerequisites
  Android Studio.

  Kotlin 1.9+.

  Android device or emulator with biometric hardware.

## Getting Started
1. Clone the Repository

   ```bash
    git clone https://github.com/yourusername/password-manager-app.git
    cd password-manager-app

2. Launch the app folder in Android Studio.

   Navigate to the directory where repository is cloned and open it.
3. Run the Project.

   Connect the Android device or start an emulator.

   Click on the Run button in Android Studio.
4. Biometric Authentication Setup
   If the device supports biometric authentication, ensure it is set up in the device settings.
    
   If not, app will itself asks the user to setup biometric authentication. If hardware doesn't supports biometric auth, it will ask to add a pin/pattern/password.

## App Structure
 - Main Components
 - MainActivity: The entry point of the app, handles the overall navigation and biometric prompt.
 - HomeScreen: Displays the list of saved passwords.
 - AddPasswordScreen: Allows users to add or edit a password entry.
 - AuthenticationScreen: Handles the biometric authentication prompt.
 - BiometricPromptManager: Manages biometric authentication logic.