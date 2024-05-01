import json

# Load data from book_v2.json
with open('book_v2.json', 'r', encoding='utf-8') as f:
    book_v2_data = json.load(f)

# Convert book_v2_data to a dictionary for easy lookup
book_v2_dict = {book['id']: book for book in book_v2_data}

# Load data from book_v3.json
with open('book_v3.json', 'r', encoding='utf-8') as f:
    book_v3_data = json.load(f)

# Modify book_v3_data
for book in book_v3_data:
    # Copy fields from book_v2_data
    corresponding_book_v2 = book_v2_dict.get(book['id'])
    if corresponding_book_v2 is not None:
        book['active'] = corresponding_book_v2['active']
        book['unit_in_stock'] = corresponding_book_v2['unit_in_stock']
        book['unit_price'] = corresponding_book_v2['unit_price']
        book['date_created'] = corresponding_book_v2['date_created']

    # Convert categories_id to integer
    book['categories_id'] = [int(id) for id in book['categories_id']]

# Write the modified data back to book_v3.json
with open('book_v3.json', 'w', encoding='utf-8') as f:
    json.dump(book_v3_data, f, ensure_ascii=False, indent=4)