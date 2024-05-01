import requests
import json

# URL request
url = "https://www.googleapis.com/books/v1/volumes?q=vật+lí&maxResults=40&key=AIzaSyDNXiwyOi6LtZWNskAvQ1xNSZs49vs4PkY"

# Gửi request và lấy dữ liệu
response = requests.get(url)
data = json.loads(response.content)

# Tạo danh sách để lưu trữ các item đáp ứng yêu cầu
filtered_items = []

# Tạo bộ để lưu trữ title đã được thêm
seen_titles = set()

# Duyệt qua dữ liệu trả về
for item in data["items"]:
    # Kiểm tra xem item có `categories` và `imageLinks` hay không
    if "categories" in item["volumeInfo"] and "imageLinks" in item["volumeInfo"]:
        # Lấy title của item
        title = item["volumeInfo"]["title"]

        # Kiểm tra xem title đã được thêm hay chưa
        if title not in seen_titles:
            # Thêm title vào bộ
            seen_titles.add(title)

            # Thêm item vào danh sách
            filtered_items.append(item)

# Mở file JSON để ghi dữ liệu
with open("vatli.json", "w", encoding="utf-8") as f:
    # Ghi dữ liệu JSON vào file
    json.dump(filtered_items, f, ensure_ascii=False, indent=4)

print("Ghi dữ liệu thành công vào truyenkieu.json!")
