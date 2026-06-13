@echo off
:: ============================================================
::  Historia Danet — Capacitor Setup Script (Windows)
::  Jalankan file ini di folder HistoriaDanetCapacitor
::  Klik kanan → Run as Administrator
:: ============================================================

title Historia Danet - Capacitor Setup

echo.
echo  ============================================
echo   Historia Danet - Setup Capacitor Android
echo  ============================================
echo.

:: Cek Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js tidak ditemukan!
    echo         Download di: https://nodejs.org
    pause
    exit /b 1
)
echo [OK] Node.js: 
node --version

:: Cek npm
npm --version >nul 2>&1
echo [OK] npm:
npm --version

:: Cek Java
java --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo [WARNING] Java tidak ditemukan!
    echo           Install JDK 17 dari: https://adoptium.net
    echo           Lanjutkan setup tapi build APK butuh Java.
    echo.
) else (
    echo [OK] Java tersedia
)

echo.
echo [1/4] Install dependencies...
call npm install
if %errorlevel% neq 0 (
    echo [ERROR] npm install gagal!
    pause
    exit /b 1
)
echo [OK] Dependencies terinstall

echo.
echo [2/4] Tambahkan platform Android...
call npx cap add android
if %errorlevel% neq 0 (
    echo [WARNING] Platform Android mungkin sudah ada, lanjutkan...
)
echo [OK] Platform Android siap

echo.
echo [3/4] Sync web app ke Android...
call npx cap sync android
if %errorlevel% neq 0 (
    echo [ERROR] Sync gagal!
    pause
    exit /b 1
)
echo [OK] Web app tersync

echo.
echo [4/4] Update AndroidManifest permissions...
:: Sudah dihandle di android/app/src/main/AndroidManifest.xml
echo [OK] Permissions akan diterapkan saat build

echo.
echo  ============================================
echo   SETUP SELESAI!
echo  ============================================
echo.
echo  Langkah selanjutnya:
echo.
echo  OPSI A - Buka di Android Studio (untuk build APK):
echo    npx cap open android
echo    Lalu di Android Studio: Build → Generate Signed APK
echo.
echo  OPSI B - Langsung run ke HP (USB debugging aktif):
echo    npx cap run android
echo.
echo  OPSI C - Build APK via command line:
echo    cd android
echo    gradlew.bat assembleRelease
echo    APK: android\app\build\outputs\apk\release\
echo.
pause
