#       Written by Julian Chytrowski, Systems Administrator @ the Buck Institute
#              and Sushanth Mukkamalla, Intern @ the Buck Institute
#       Sept. 2015
#		June. 2016


#!/usr/bin/python

#Lets make a script which helps pipe CLI programs together, and reports errors!
import sys
import os
import subprocess
import glob
import datetime
import smtplib
import argparse
import shutil
from email.mime.text import MIMEText

#################################################################################
#
# Todo/wishlist
# * 
# * Daemonize / turn this script into a perpetual background process
# 	*lockfile detection? 
# * Add timeout detection
# * I haven't vetted move_to_bigrock (or any of the windows commands for that
#   matter
# * What is the umpire output file type? Add that to the list near the
#   end of the main(), or you won't get the output into the network store.
# * change log files and stuff to windows format
# 
################################################################################
#   fyi's: UNC's should be fine, unless you try to do fancy stuff.
#   (that means output_directory should be '//bigrock/blah/whatever'
#
#################################################################################

###############################################################################
#
# Global vars
#
##############################################################################

user='youremail@emailserver.org'
scratch_dir=sys.argv[1]
#log_file=sys.argv[1]
output_directory=sys.argv[2]
error_list = []
#usr=sys.argv[3]
#psw=sys.argv[4]
#fromaddr=sys.argv[3]
#toaddr=sys.argv[3]

################################################################################
#
# functions, etc
#
################################################################################

def ensure_dir(directory):
	try:
		if not os.path.exists(directory):
			os.makedirs(directory)
	except Exception, e:
		print "Directory '" + directory +"' does not exist, and could not be created."

def ensure_file(f):
	try:
		open(f,'a').close()
	except Exception, e:
		print "File '" + f +"' does not exist, and could not be created."

# strip extension from filename
def stripped(file_name):
	root,ext = os.path.splitext(file_name)
	return str(root)
'''
# send an email... 
def noticeEMail(starttime, usr, psw, fromaddr, toaddr):
    # Calculate run time
    runtime=datetime.datetime.now() - starttime
    
    # Initialize SMTP server
    server=smtplib.SMTP('smtp.gmail.com:587')
    server.starttls()
    server.login(usr,psw)
    
    # Send email
    senddate=datetime.datetime.strftime(datetime.datetime.now(), '%Y-%m-%d')
    subject="Pipeline Process Completed"
    m="Date: %s\r\nFrom: %s\r\nTo: %s\r\nSubject: %s\r\nX-Mailer: My-Mail\r\n\r\n" % (senddate, fromaddr, toaddr, subject)
    msg=
	
    #Job runtime: +str(runtime)
    
    server.sendmail(fromaddr, toaddr, m+msg)
    server.quit()
'''
#log error to disk
def log_cmd_err(command,err_output):
	target = open(log_file, 'a')
	err_string = str(datetime.datetime.now())+":Command{"+" ".join(command)+"} threw error:"+err_output+'\n'
	target.write(err_string)
	target.close()
	error_list.append(err_string)
	
#control errors
def log_control_err(err_string):
	target = open(log_file, 'a')
	target.write(str(datetime.datetime.now())+":"+err_string+'\n')
        target.close()
	error_list.append(err_string)

#run subprocess
def run_subprocess(command):
	# subprocess commands are expected as a list
	# output needs to be shunted to pipes, otherwise try-except errors will fail 
	# into the catch clause, instead of reporting the actual error output of the 
	# command itself, just the error return code. Annoying.
	p = subprocess.Popen(command,stdout=subprocess.PIPE,stderr=subprocess.PIPE)
	stdout,stderr = p.communicate()
	if stderr != "":
		log_cmd_err(command,stderr.replace("\n", ";"))
		return 1
	else:
		return 0
		
#run subprocess to convert mzxml file to mgf files
def run_mgf_subprocess(command):
	strCommand = ' '.join(command)
	#print strCommand
	p = subprocess.Popen(strCommand, stdin=subprocess.PIPE, stdout=subprocess.PIPE)
	stdout,stderr = p.communicate()

#wiff to mzml (Absciex data converter)
def wiff_to_mzml(input_file,debug=False):
	if debug:
		command = ["cp", input_file, stripped(input_file)+".mzml"]
	else:
		command = ["C:\Program Files (x86)\AB SCIEX\MS Data Converter\AB_SCIEX_MS_Converter.exe", "WIFF", input_file, "-centroid", "MZML", stripped(str(input_file))+".mzml"]
	run_subprocess(command)

#mzml to mzxml (proteowizard)
def mzml_to_mzxml(input_file,debug=False):
	if debug:
                command = ["cp", input_file, stripped(input_file)+".mzxml"]
	else:
		#
		#
		#ASK FOR VERSION OF PROTEOWIZARD
		#
		#
		command = ["C:\Program Files\ProteoWizard\ProteoWizard 3.0.8708\msconvert.exe", input_file, "--mzXML", "--32", "--zlib", "-o", scratch_dir ]	
	run_subprocess(command)

#verifying mzxml is correct
def verify_mzxml(input_file,debug=False):
	if debug:
		command=['echo','hi']
		return(0)
        else:
		#print "please add the full path to indexzXML in the verify_mzxml function"
		#exit(1)
		#WHERE IS THIS FILE
		command = ["C:\indexmzXML.exe", input_file]
		run_subprocess(command)
		if os.path.exists(input_file+'.new'):
                        shutil.move(scratch_dir+input_file+".new", scratch_dir+stripped(str(input_file))+".mzxml")

#mzxml to mgf (DIA-umpire)
def umpire_mzxml(input_file,debug=False):
        if debug:
			command = ["cp", input_file, stripped(input_file)+".umpire"]
        else:
			#
			#
			# -Xmx60G might change
			#
			#
			command = ["java", "-Xmx60G", "-jar", "C:\DIA-Umpire_v1_4254\DIA_Umpire_SE.jar", input_file, "C:\DIA-Umpire_v1_4254\KacVar_5min_1to4isotopes.se_params", "pause"]
	run_mgf_subprocess(command)
		
#mgf to mzxml (proteowizard)
def mgf_to_mzxml(input_file,debug=False):
	if debug:
                command = ["cp", input_file, stripped(input_file)+".mzxml"]
	else:
		#
		#
		#ASK FOR VERSION OF PROTEOWIZARD
		#
		#
		command = ["C:\Program Files\ProteoWizard\ProteoWizard 3.0.8708\msconvert.exe", input_file, "--mzXML", "--32", "--zlib", "-o", scratch_dir ]	
	run_subprocess(command)
		
def move_to_bigrock(input_file, force=False,debug=False):
	#check if file already exists there?
	if debug:
		command = ["cp", input_file, output_directory]
	else:
		if force:
			command = ["copy", "/V", input_file, output_directory ]
		else:
			command = ["copy", "/V", input_file, output_directory ]
	return(run_subprocess(command))


def main():
	# Start time of script
	#starttime=datetime.datetime.now()
    
	ensure_dir(scratch_dir)
	ensure_dir(output_directory)

	os.chdir(scratch_dir)
	#ensure_file(log_file)
	outputfile='debug'
	
	"""
	parser = argparse.ArgumentParser(description="wiff to umpire cli pipe")
	parser.add_argument('-d', '--debug', help="Debug flags.", action='store_true', default=False)
	parser.add_argument('-e', '--email', nargs='?', const=user, help="Address to notify when run completes [default is User var specified in head of script]")
	parser.add_argument('-f', '--force', help="Force overwrite if file exists", action='store_true', default=False)
	args = parser.parse_args()

	#for input_file in list_wiff_inputs()
	#check_lock()

	if args.debug:
		open('debug.wiff','a').close()
	"""
	
	file_count = []	
	file_types = ('*.wiff','*.mzml','*.mzxml')
	for files in file_types:
		file_count.extend(glob.glob(files))
	if len(file_count) == 0:
		#log_control_err('No wiff, mzml, or mzxml files found, exiting')
		print 'No wiff, mzml, or mzxml files found, exiting'
		exit(1)
	

	print 'Converting .wiff to .mzml...\n'
	for input_file in glob.glob('*.wiff'):
		wiff_to_mzml(input_file)
		#wiff_to_mzml(input_file,args.debug)
	
	
	print 'Converting .mzml to .mzxml...\n'
	for input_file in glob.glob('*.mzml'):
		mzml_to_mzxml(input_file)
		#mzml_to_mzxml(input_file,args.debug)
	
	
	print 'Verifying .mzxml files...\n'
	for input_file in glob.glob('*.mzxml'):
		verify_mzxml(input_file)
		#verify_mzxml(input_file,args.debug)
	
	
	print 'Converting .mzxml to .mgf...\n'
	for input_file in glob.glob('*.mzxml'):
		umpire_mzxml(input_file)
		#umpire_mzxml(input_file,args.debug)
	
	
	print 'Converting .mfg to .mzxml'
	for input_file in glob.glob('*.mgf'):
		mgf_to_mzxml(input_file)
		#mgf_to_mzxml(input_file,args.debug)

		
	#move to bigrock, rebuilding files cuz maybe we generated more.
	file_count_refreshed = [] 
	file_types = ('*.wiff','*.mzml','*.mzxml','*.txt','*.mgf')
	for files in file_types:
                file_count_refreshed.extend(glob.glob(files))
	for files in file_count_refreshed:
		if move_to_bigrock(files,args.force,args.debug) == 0:
			os.remove(files)
	send_notification(args.email)
	
	"""
	if sys.argv[5] == "y" or sys.argv[5] == "Y" or sys.argv[5] == "yes" or sys.argv[5] == "Yes":
		print 'Emailing user'
		try:
			noticeEMail(starttime, usr, psw, fromaddr, toaddr)
		except Exception, e:
			print 'Email or Password may have been entered incorrectly.'
	"""
	
	print 'Pipeline process completed'

if __name__ == "__main__":
	sys.exit(main())
