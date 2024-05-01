import json

# Load data from book_v3.json
with open('book_v3.json', 'r', encoding='utf-8') as f:
    book_v3_data = json.load(f)

# Update the id field
for i, book in enumerate(book_v3_data, start=1):
    book['id'] = i

# Write the modified data back to book_v3.json
with open('book_v3.json', 'w', encoding='utf-8') as f:
    json.dump(book_v3_data, f, ensure_ascii=False, indent=4)