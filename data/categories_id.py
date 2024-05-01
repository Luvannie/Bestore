import csv
import json

# Read the category map and create a dictionary
category_map = {}
with open('category_map.csv', 'r') as f:
    reader = csv.reader(f)
    next(reader)  # Skip the header
    for row in reader:
        category_id, category_name = row
        category_map[category_name] = category_id

# Read the book data
with open('book_v1.json', 'r',encoding="utf-8") as f:
    book_data = json.load(f)

# Replace the categories with category IDs
for book in book_data:
    book['categories_id'] = [category_map[category] for category in book['categories'] if category in category_map]
    del book['categories']

# Write the modified data to a new file
with open('book_v3.json', 'w',encoding="utf-8") as f:
    json.dump(book_data, f, ensure_ascii=False, indent=4)