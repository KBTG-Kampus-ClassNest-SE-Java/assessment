# Note

สำหรับบาง case ที่ทาง KBTG ไม่ได้ระบุเงื่อนไขมาอย่างชัดเจน หรือผมอาจจะไม่เห็นใน discord ว่ามีการ discussion topic นี้กันแล้ว ผมจะขอ note สิ่งที่ผมได้เขียนลงไปเพิ่มเติมลงใน **NOTE.md** ครับ

## EXP01

- การเพิ่ม lottery นั้นไม่สามารถเพิ่ม lottery ที่มีเลขซ้ำกันได้

## EXP02

- การ list lottery จาก API path **GET/lotteries** จะเป็นการ list lotteries ทั้งหมดที่สามารถทำการซื้อขายได้ **(lottery ที่มีจำนวนคงเหลือมากกว่า 0)**

## EXP04

- การ list lottery ทั้งหมดที่เคยซื้อนั้น lottery จะแสดงแค่เลขละ 1 ครั้งเท่านั้น เช่น ซื้อ 000001 2 ใบกับ 000002 3 ใบ ซึ่งมีราคาใบละ 80 บาท จะได้ผลลัพธ์คือ
  ```json
  {
    "tickets": ["000001", "000002"],
    "count": 5,
    "cost": 400
  }
  ```

## EXP05

- Error ที่เกิดจากการขาย lottery คืนจาก API path **DELETE /users/:userId/lotteries/:ticketId** จะเป็น 400 Bad request ไม่ใช่ 404 Not found เนื่องจาก user ทำการขาย lottery ที่ไม่ได้ซื้อไว้(อาจจะมีหรือไม่มี lottery เลขนั้นก็ได้ ดังนั้นการ return ด้วย 404 Not found จึงไม่ถูกต้องสำหรับกรณีที่ lottery ใบนั้นมีอยู่จริงแต่ user ไม่ได้ซื้อไว้)
- ในการซื้อ lottery นั้นสามารถซื้อได้แค่ครั้งละ 1 ใบ/ครั้ง เนื่องจากไม่ได้มีการระบุจำนวนเข้ามาใน request param ด้วย
- การขาย lotteries จะเป็นการขาย lotteries ที่มีเลขนั้นทิ้ง**ทั้งหมด** เช่น **_DELETE /users/:1234567890/lotteries/:111111_** หากมี lottery ที่มีเลข 111111 อยู่ 6 ใบก็จะขายทิ้งทั้งหมด

## Design pattern
- ผมได้ใช้ Pattern แบบ ***N-tier architecture และไม่ได้ทำการ Vertical slicing*** เนื่องจากผมมองว่าจาก requirements ใน README.md ที่ทาง KBTG บอกว่านี่เป็น Features ใหม่ของ KBTG ดังนั้นนี่จึงเป็นเหมือน 1 module เล็ก ๆ จึงไม่ได้มีการ Vertical Slicing เพิ่มเติม เพราะถือว่านี่คือ 1 module ย่อย ๆ แล้ว
- นอกจากนี้ทุก ๆ service ยังคงต้องใช้ repository เดียวกัน ซึ่งหากแยกออกไปจะต้องไปสร้าง repository แยกกันใน module ย่อย ๆ ซึ่งทำให้เกิด redundancy 
