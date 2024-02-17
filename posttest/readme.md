### Business Requirements
* [x] แอดมินสามารถเพิ่มข้อมูลลอตเตอรี่เป็นตัวเลข 6 หลักได้
* [ ] ผู้ใช้สามารถแสดงรายการลอตเตอรี่ที่มีทั้งหมดได้
* [ ] ผู้ใช้สามารถซื้อลอตเตอรี่ได้
* [ ] ผู้ใช้สามารถแสดงรายการลอตเตอรี่ที่ตัวเองซื้อได้ พร้อมสรุปราคารวมที่ซื้อทั้งหมดได้
* [ ] ผู้ใช้สามารถขายคืนลอตเตอรี่ที่ตัวเองซื้อได้
* [ ] ไม่ต้องคำนึงถึงเงินที่ผู้ใช้จะต้องจ่าย ระบบอื่นมีการตรวจสอบอยู่แล้ว UserID ของผู้ใช้เป็นตัวเลข 10 หลักเท่านั้น
* [ ] ฟีเจอร์ของแอดมินต้องมีการตรวจสอบสิทธิก่อนถึงจะใช้ได้
* [ ] ไม่มีงวดของลอตเตอรี่มาเกี่ยวข้อง

### Prerequisites
* [x] โปรเจคตั้งต้นคือ[โปรเจคนี้] (https://github.com/KBTG-Kampus-ClassNest-SE-Java/assessment)
* [x] กำหนดให้ส่งลิ้งค์คำตอบคือ github repository ที่เก็บโค้ดของคุณ https://github.com/<your github name>/assessment ตั้งเป็น public
* [x] จะต้อง มีการใช้งาน PostgreSQL
* [x] จะต้อง มีการใช้งานสร้าง Table ชื่อ lottery เพื่อใช้จัดการกับลอตเตอรี่และ user_ticket เพื่อใช้เก็บรายการซื้อขายของผู้ใช้งาน
* [x] จะต้อง เปิดใช้งานได้ผ่าน port 8888
* [ ] จะต้อง เรียกใช้ Database URL ผ่านทาง Environment variable ชื่อ DATABASE_URL ได้

### Story: EXP01
#### default
* [x] POST /admin/lotteries
* [x] ต้องยืนยันสิทธิ์การเข้าใช้งานด้วย basic authentication
* [x] Request Body
* [x] Response Body
#### extra
* [x] login admin and user (inmemory)
* [x] admin create lottery and save to postgres database
### Story: EXP02
#### default
* [x] GET /lotteries
* [x] Response Body
#### extra
* [ ] admin/ user read lotteries all from postgres database