import sqlite3

# Sample code which contain SQL injection vulnerability
user_input_username = "admin' OR '1'='1" 
user_input_password = "password"

db = sqlite3.connect(":memory:")
cursor = db.cursor()

cursor.execute("CREATE TABLE users (username TEXT, password TEXT)")
cursor.execute("INSERT INTO users VALUES ('admin', 'secret_password')")
db.commit()

# --- VULNERABLE CODE START  ---
# A query is constructed by concatenating user input directly into the SQL string.
# This is an UNSAFE and vulnerable practice.
vulnerable_query = "SELECT * FROM users WHERE username = '" + user_input_username + "' AND password = '" + user_input_password + "';"

# The malicious input effectively bypasses authentication.
# The query becomes: "SELECT * FROM users WHERE username = 'admin' OR '1'='1' AND password = 'password';"
cursor.execute(vulnerable_query)
result = cursor.fetchall()

if result:
    print("\nVULNERABLE: Login successful!")
    print(f"User data: {result}")
else:
    print("Login failed.")
