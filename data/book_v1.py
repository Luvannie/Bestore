import json

# Mở file JSON
with open('truyenkieu.json', 'r',encoding='utf-8') as f:
    data = json.load(f)

# Tạo danh sách để lưu trữ dữ liệu đã lọc
filtered_data = []

# Duyệt qua từng item trong dữ liệu
for item in data:
    # Lấy thông tin cần thiết
    volume_info = item.get('volumeInfo', {})
    image_links = volume_info.get('imageLinks', {})
    filtered_item = {
        "id": item.get("id"),
        "title": volume_info.get("title"),
        "authors": volume_info.get("authors"),
        "publisher": volume_info.get("publisher"),
        "publishedDate": volume_info.get("publishedDate"),
        "description": volume_info.get("description"),
        "categories": volume_info.get("categories"),
        "thumbnail": image_links.get("thumbnail"),
        "language": volume_info.get("language"),
    }
    filtered_data.append(filtered_item)

# Lưu dữ liệu đã lọc vào file mới
with open('filtered_books.json', 'a+',encoding='utf-8') as f:
    json.dump(filtered_data, f, ensure_ascii=False, indent=4)