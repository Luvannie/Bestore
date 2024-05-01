import json
import random
from datetime import datetime

# Đọc dữ liệu từ file book_v2.json
with open('book_v2.json', 'r',encoding="utf-8") as f:
    data = json.load(f)

# Thêm các trường mới vào mỗi item
for book in data:
    book['active'] = 1
    book['unit_in_stock'] = random.randint(1, 100)
    book['unit_price'] = random.randint(10000, 1000000)
    start_date = datetime(2020, 1, 1)
    end_date = datetime(2024, 4, 10)
    random_date = start_date + (end_date - start_date) * random.random()
    book['date_created'] = random_date.strftime("%Y-%m-%d")

# Ghi dữ liệu đã cập nhật vào file book_v2.json
with open('book_v2.json', 'w') as f:
    json.dump(data, f, ensure_ascii=False, indent=4)
