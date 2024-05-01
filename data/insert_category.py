import json
import mysql.connector

# Connect to the database
db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="nhatanh25",
    database="book_store"
)

with open('categories_map.json', 'r') as f:
    data = json.load(f)

# Process and insert data into the table
cursor = db.cursor()
for category_name, category_id in data.items():
    # Extract the category name without any leading or trailing characters
    clean_category_name = category_name.replace("('", "").replace("',)", "").replace("(\"", "").replace("\"", "")

    # Only get the first part of the category name
    clean_category_name = clean_category_name.split(',')[0]

    # Only execute the SQL query if clean_category_name is not empty
    if clean_category_name:
        # Prepare the SQL query with a placeholder
        sql = "INSERT INTO book_category (category_name) VALUES (%s)"

        # Execute the SQL query with the parameter
        cursor.execute(sql, (clean_category_name,))

db.commit()
cursor.close()
db.close()