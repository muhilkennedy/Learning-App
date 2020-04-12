@echo off
:: change directory to angular modular
cd AngularModule
@echo "####################################################"
@echo "####################################################"
@echo Compiling Angular Module
@echo "####################################################"
@echo "####################################################"

:: compile and build angular module command
npm install

@echo "####################################################"
@echo "####################################################"
@echo Starting Node Server
@echo "####################################################"
@echo "####################################################"

::command to start angular server
ng serve
