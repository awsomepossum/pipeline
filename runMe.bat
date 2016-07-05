@echo off

set CurrentDir=%CD%

:check_which_input
set /p checkInput= Do you have a parameters file for the runMe (ex: ***.parameters) [y/n]: 
IF "%checkInput%"=="y" goto enter_param_file
IF "%checkInput%"=="Y" goto enter_param_file
IF "%checkInput%"=="yes" goto enter_param_file
IF "%checkInput%"=="Yes" goto enter_param_file
IF "%checkInput%"=="n" goto get_info
IF "%checkInput%"=="N" goto get_info
IF "%checkInput%"=="no" goto get_info
IF "%checkInput%"=="No" goto get_info
goto scratch_dir
::cls


:get_info
echo:
echo Fill in all the fields.
echo:
javac GetInput.java
java GetInput > input.parameters
rm *.class
echo running again with the parameters entered.
runMe.bat < input.parameters


:scratch_dir
set /p ScratchDir= Enter scratch dir: 
IF EXIST %ScratchDir% (
echo Directory exists
echo:
) ELSE (
echo This directory does not exist. Enter another. (press control c to exit^)
echo:
goto scratch_dir
)


:output_dir
set /p OutputDir= Enter output directory: 
IF EXIST %OutputDir% (
echo Directory exists
echo:
) ELSE (
echo This directory does not exist. Enter another. (press control c to exit^)
echo:
goto output_dir
)


:number_threads
set /p NumOfThreads= Enter the number of threads on this machine: 
echo:


:amount_ram
set /p AmountOfRam= Enter the amount of ram on this machine: 
echo:


:absciex_ms_convert_exe
set /p ABSCIEXMSConvertExe= Enter the full path to AB_SCIEX_MS_Convert.exe: 
IF EXIST %ABSCIEXMSConvertExe% (
echo .exe exists
echo:
) ELSE (
echo The .exe does not exist here. Enter another location. (press control c to exit^)
echo:
goto absciex_ms_convert_exe
)


:ms_convert_exe
set /p MSConvertExe= Enter the full path to msconvert.exe: 
IF EXIST %MSConvertExe% (
echo .exe exists
echo:
) ELSE (
echo The .exe does not exist here. Enter another location. (press control c to exit^)
echo:
goto ms_convert_exe
)


:indexmxXML_exe
set /p IndexMZXMLExe= Enter the full path to indexmzXML.exe: 
IF EXIST %IndexMZXMLExe% (
echo .exe exists
echo:
) ELSE (
echo The .exe does not exist here. Enter another location. (press control c to exit^)
echo:
goto indexmxXML_exe
)


:DIA_Umpire_SE_jar
set /p DIAUmpireSEJar= Enter the full path to DIA_Umpire_SE.jar: 
IF EXIST %DIAUmpireSEJar% (
echo .exe exists
echo:
) ELSE (
echo The .jar does not exist here. Enter another location. (press control c to exit^)
echo:
goto DIA_Umpire_SE_jar
)


:fasta_dir
echo:
echo:
echo For MSGF search...
set /p FastaDir= Enter the location of the .fasta file (ex: C:\...\20150427_mouse_sprot.cc.fasta): 
IF EXIST %FastaDir% (
echo File exists
echo:
) ELSE (
echo This file does not exist. Enter another. (press control c to exit^)
echo:
goto fasta_dir
)


:enter_ppm
set /p Ppm= Enter ppm (ex: 25ppm): 
echo:


:enter_e
set /p Enzyme= Enter -e "enzyme" (ex: 5): 
echo:


:enter_mods_file
echo Enter the location of the mods .txt file (ex: C:\...\Mods_acetyl.txt)
set /p ModsFileLocation= Enter correctly: 


:enter_params.xml
echo:
echo:
echo For X!Tandem search...
set /p ParamsXml= Enter the location of the _params.xml file (ex: C:\...\tandem_Ksuc_params.xml): 
IF EXIST %ParamsXml% (
echo File exists
echo:
) ELSE (
echo This file does not exist. Enter another. (press control c to ecit^)
echo:
goto enter_params.xml
)


:tandem_exe
set /p TandemExe= Enter the full path to tandem.exe: 
IF EXIST %TandemExe% (
echo .exe exists
echo:
) ELSE (
echo The .exe does not exist here. Enter another location. (press control c to exit^)
echo:
goto tandem_exe
)


:tandem2XML_exe
set /p Tandem2XMLExe= Enter the full path to tandem.exe: 
IF EXIST %Tandem2XMLExe% (
echo .exe exists
echo:
goto end_process
) ELSE (
echo The .exe does not exist here. Enter another location. (press control c to exit^)
echo:
goto tandem2XML_exe
)


:send_email_check
set /p sendemail= Do you want to receive an email when the pipeline process has completed [y/n]: 
IF "%sendemail%"=="y" goto enter_email
IF "%sendemail%"=="Y" goto enter_email
IF "%sendemail%"=="yes" goto enter_email
IF "%sendemail%"=="Yes" goto enter_email
set email=n
set password=n
set sendemail=n
goto run_program



:run_program
echo Running program.
echo:
diaUmpire_pipe.py %ScratchDir% %OutputDir%
goto end_process
::goto MSGF_Search



:enter_email
set /p email= Enter email address: 
set /p emailAuth= Confirm email address: 
IF "%email%"=="%emailAuth%" (
echo:
goto enter_password
) ELSE (
echo Email addresses do not match. Enter again. (press control c to exit^)
echo:
goto enter_email
)

:enter_password
powershell -Command $pword = read-host "Enter email password " -AsSecureString ; $BSTR=[System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($pword) ; [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR) > .tmp.txt & set /p password=<.tmp.txt & del .tmp.txt
powershell -Command $pword = read-host "Confirm email password " -AsSecureString ; $BSTR=[System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($pword) ; [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR) > .tmp.txt & set /p passwordAuth=<.tmp.txt & del .tmp.txt 
IF "%password%"=="%passwordAuth%" (
echo:
goto run_program
) ELSE (
echo Email passwords do not match. Enter again. (press control c to exit ^)
echo:
goto enter_password
)

:MSGF_Search
echo:
echo Creating MSGF .bat file
echo:
javac MSGFSearch.java
java MSGFSearch %OutputDir% %FastaDir% %ppm% %enzyme% %modsFileLocation% > MSGFsearch.bat
rm MSGFSearch.class
echo Running MSGFsearch.bat
echo:
call MSGFsearch.bat
mv -f MSGFsearch.bat %OutputDir%
goto Creating


:Creating
echo Creating .tandem.params files for X!Tandem search
::javac CreateTandemParams.java
::java CreateTandemParams
goto end_process


:enter_param_file
IF EXIST parsed.parameters (
rm parsed.parameters
)
set /p parametersFile= Enter the location of the parameters file (ex: C:\...\***.parameters): 
IF EXIST %parametersFile% (
echo File exists
echo:
touch parsed.parameters
javac parseParameters.java
java parseParameters %parametersFile%
rm parseParameters.class
runMe.bat < parsed.parameters > PipelineLog.txt
IF EXIST PipelineLog.txt (
mv -f PipelineLog.txt %OutputDir%
)
IF EXIST parsed.parameters (
mv -f parsed.parameters %OutputDir%
)
) ELSE (
echo File does not exist.
echo:
goto check_which_input
)
goto end_process


:end_process
echo success!!!!
@pause