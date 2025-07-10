# project-on-Student-Management-System-using-java-and-mysql-connectivity
New Repository with Latest Student Management System

#**General Scheema Used In createing the above code**

menu
1. New user sign up
    a. Admin
        i. Name
        ii. Email
        iii. Phone No.
        iv. designation
        v. Username
        vi. Password

    b. Teacher
        i. Name
        ii. Email
        iii. Phone No.
        iv. course
        v. Username
        vi. Password

    c. temporary Student
        i. Name
        ii. Email
        iii. Phone No.
        iv. Username
        v. Password

    d. User
        i. Name
        ii. Email
        iii. Phone No.
        iv. Username
        v. Password

 
2. Log in
   a. admin
       i. Username
       ii. Password
		if matches then
			1. add student
				i. Name
        			ii. Email
        			iii. Phone No.
				iv. Class(1-12)			{classes from 1-10 and 11 and 12th} {if class== 11 or 12th ask for stream: Arts/Commerce/Science (PCM/PCB/PCMB)}
				vi. No. of subjects
				vii. subject name(s)
        			viii. Username
        			ix. Password
				x. exit to logged in page
			2. remove student
				i. Name
				ii. Unique_id
				iii. Class
				iv. exit to logged in page
			3. Add admin
        			i. Name
        			ii. Email
        			iii. Phone No.
        			iv. designation
        			v. Username
        			vi. Password
				vii. exit to logged in page
			4. Remove Admin
				i. Name
				ii. Username
				iii. Designation
				iv. exit to logged in page
			5. Add Teacher      
				i. Name
        			ii. Email
        			iii. Phone No.
        			iv. course
        			iv. Username
        			v. Password
				vi. exit to logged in page
			6. Remove teacher
				i. Name
				ii. username
				iii. course
				iv. Exit to logged in page
			7. Remove user
				i. show all users
				ii. select using username
					a. username
				iii. exit to logged in page
			8.  Log Out (exit the loop)

   b. Teacher
       i. Username
       ii. Password
		if matches then
			1. add marks
				i. show students with subjects
				ii. add marks using particular subject
					a. name
					b. unique_id
					c. enter subject/subjects of which the data need to be edit
				iii. edit makes of some subject/subjects for some duration days
					a. name
					b. unique_id
						if matched then
							-> Starting date
							-> Last date
				iv. exit to logged in page
			2. edit marks
				i. show students with subjects
				ii. edit makes using particular subject
					a. name
					b. unique_id
					c. enter subject/subjects of which the data need to be edit
				iii. edit makes of some subject/subjects for some duration days
					a. name
					b. unique_id
						if matched then
							-> Starting date
							-> Last date
				iv. exit to logged in page
			3. remove marks
				i. show students with subjects
				ii. remove makes using particular subject
					a. name
					b. unique_id
					c. enter subject/subjects of which the data need to be removed
				iii. remove makes of some subject/subjects for some duration days
					a. name
					b. unique_id
						if matched then
							-> Starting date
							-> Last date
				iv. exit to logged in page
			4. add attendance of the student
				i. show all students
				ii. add using particular day
					a. name
					b. unique_id
					c. enter date of which attendance to be added
				iii. add attendance for some duration days
					a. name
					b. unique_id
						if matched then
							-> Starting date
							-> Last date
				iv. add attendance of some separate days
					a. name
					b. unique_id
						if matched then in loop
							-> enter the dates that attendance need to be add
				v. exit to logged in page
			5. remove attendance of the student
				i. show students
				ii. remove using particular day
					a. name
					b. unique_id
					c. enter date of which attendance to be removed
				iii. remove attendance for some duration days
					a. name
					b. unique_id
						if matched then
							-> Starting date
							-> Last date
				iv. remove attendance of some separate days
					a. name
					b. unique_id
						if matched, then
							-> enter the dates that attendance need to be removed
				v. exit to logged in page
			6.  Log Out (exit the loop)

   c. temporary student
	i. username
	ii. Password
		if matched then in loop
			1. Display leader board
				i. class
			2. Display all teacher names
			3. Display all faculty names
			4. Log Out

   d. Student
        i. username
        ii. Password
		if matches then in loop
			1. display marks
				i. name
				ii. unique_id
				iii. class
				iv. exit to logged in page
			2. display class performance
				i. name
				ii. unique_id
				iii. class
				iv. exit to logged in page
			3. percentage of the classes attended
				i. name
				ii. unique_id (primary key) 
				iii. class
				iv. exit to logged in page
			4.  Log Out (exit the loop)
	iii. change password

   e. User
       i. Username
       ii. Password
		if matches, then in loop
			1. display all students marks with aggregate percentage
			2. display the teacher's name.
			3. Log Out
