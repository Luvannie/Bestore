import json
import random
from datetime import datetime
import ast

# Đọc dữ liệu từ file book_v1.json
with open('book_v1.json', 'r', encoding="utf-8") as f:
    data = json.load(f)

# Tạo map categories_id -> categories
categories_map = {}
categories_id = 1

# Thêm các trường mới vào mỗi item và cập nhật categories_id
for book in data:
    # Lấy categories từ book và chuyển đổi nó thành tuple
    categories = tuple(book['categories'])

    # Tìm kiếm categories trong map
    if categories in categories_map:
        book['categories_id'] = categories_map[categories]
    else:
        book['categories_id'] = categories_id
        categories_map[categories] = categories_id
        categories_id += 1

    # Xóa trường categories
    del book['categories']

    # Thêm các trường mới
    book['active'] = 1
    book['unit_in_stock'] = random.randint(1, 100)
    book['unit_price'] = random.randint(10000, 1000000)
    start_date = datetime(2020, 1, 1)
    end_date = datetime(2024, 4, 10)
    random_date = start_date + (end_date - start_date) * random.random()
    book['date_created'] = random_date.strftime("%Y-%m-%d")

# Chuyển đổi khóa từ tuple thành chuỗi
categories_map_str_keys = {str(key): value for key, value in categories_map.items()}

# Lưu categories_map vào tệp
with open('categories_map.json', 'w', encoding="utf-8") as f:
    json.dump(categories_map_str_keys, f, indent=4)

# Ghi dữ liệu đã cập nhật vào file book_v2.json
with open('book_v2.json', 'w', encoding="utf-8") as f:
    json.dump(data, f, ensure_ascii=False, indent=4)