import mysql.connector
import json

# Kết nối đến cơ sở dữ liệu
db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="nhatanh25",
    database="book_store"
)

cursor = db.cursor()

# Mở file JSON với mã hóa 'utf-8'
with open('book_v3.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

# Duyệt qua từng item trong dữ liệu
for item in data:
    id = item['id']
    title = item['title']
    authors = ', '.join(item['authors']) if isinstance(item['authors'], (list, tuple)) else item['authors']
    publisher = item['publisher']
    publishedDate = item.get('publishedDate')  # Lấy giá trị publishedDate nếu có
    description = item['description']
    categories_id = item['categories_id'][0] if item['categories_id'] else None
    thumbnail = item['thumbnail']
    language = item['language']
    active = item['active']
    units_in_stock = item['unit_in_stock']
    unit_price = item['unit_price']
    date_created = item['date_created']

    # Chuẩn bị giá trị cho truy vấn INSERT
    values = (id, title, authors, publisher, publishedDate, description, categories_id, thumbnail, language, active, units_in_stock, unit_price, date_created)

    # Tạo truy vấn INSERT
    sql = """
        INSERT INTO books (id, title, authors, publisher, publishedDate, description, category_id, thumbnail, language, active, units_in_stock, unit_price, date_created)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """

    try:
        cursor.execute(sql, values)
        db.commit()
        # In thông báo với mã hóa 'utf-8' và thay thế các ký tự không thể mã hóa
        print(f"Imported book: {title}".encode('utf-8', 'replace'))
    except Exception as e:
        # In lỗi với mã hóa 'utf-8' và thay thế các ký tự không thể mã hóa
        print(f"Error importing book: {title} - {str(e)}".encode('utf-8', 'replace'))

# Đóng kết nối
db.close()