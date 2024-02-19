### Business Requirements
* [x] แอดมินสามารถเพิ่มข้อมูลลอตเตอรี่เป็นตัวเลข 6 หลักได้
* [x] ผู้ใช้สามารถแสดงรายการลอตเตอรี่ที่มีทั้งหมดได้
* [x] ผู้ใช้สามารถซื้อลอตเตอรี่ได้
* [x] ผู้ใช้สามารถแสดงรายการลอตเตอรี่ที่ตัวเองซื้อได้ พร้อมสรุปราคารวมที่ซื้อทั้งหมดได้
* [x] ผู้ใช้สามารถขายคืนลอตเตอรี่ที่ตัวเองซื้อได้
* [x] ไม่ต้องคำนึงถึงเงินที่ผู้ใช้จะต้องจ่าย ระบบอื่นมีการตรวจสอบอยู่แล้ว UserID ของผู้ใช้เป็นตัวเลข 10 หลักเท่านั้น
* [x] ฟีเจอร์ของแอดมินต้องมีการตรวจสอบสิทธิก่อนถึงจะใช้ได้
* [x] ไม่มีงวดของลอตเตอรี่มาเกี่ยวข้อง

### Prerequisites
* [x] โปรเจคตั้งต้นคือ[โปรเจคนี้] (https://github.com/KBTG-Kampus-ClassNest-SE-Java/assessment)
* [x] กำหนดให้ส่งลิ้งค์คำตอบคือ github repository ที่เก็บโค้ดของคุณ https://github.com/<your github name>/assessment ตั้งเป็น public
* [x] จะต้อง มีการใช้งาน PostgreSQL
* [x] จะต้อง มีการใช้งานสร้าง Table ชื่อ lottery เพื่อใช้จัดการกับลอตเตอรี่และ user_ticket เพื่อใช้เก็บรายการซื้อขายของผู้ใช้งาน
* [x] จะต้อง เปิดใช้งานได้ผ่าน port 8888
* [ ] จะต้อง เรียกใช้ Database URL ผ่านทาง Environment variable ชื่อ DATABASE_URL ได้

### EXP01
#### default
* [x] POST /admin/lotteries
* [x] ต้องยืนยันสิทธิ์การเข้าใช้งานด้วย basic authentication
* [x] Request Body
* [x] Response Body
#### extra
* [x] login admin and user (inmemory)
* [x] admin create lottery and save to postgres database

### EXP02
#### default
* [x] GET /lotteries
* [x] Response Body
#### extra
* [ ] admin/ user read lotteries all from postgres database

### EXP03
* [x] POST /users/:userId/lotteries/:ticketId
* [x] userId และ ticketId เป็นค่าที่ผู้ใช้ป้อนเข้ามา
* [x] Response Body

### EXP04
* [x] ในฐานะผู้ใช้ ฉันต้องการดูรายการลอตเตอรี่ทั้งหมดที่เคยซื้อ
* [x] Response Body

### EXP05
* [x] DELETE /users/:userId/lotteries/:ticketId
* [x] userId และ ticketId เป็นค่าที่ผู้ใช้ป้อนเข้ามา
* [x] Response Body

### Acceptance Guidelines
* [x] Config Git โดยใช้ชื่อและอีเมล์ที่ถูกตามรูปแบบ
* [x] ทำ Pull request ผ่าน Github
* [x] ระบบสามารถรันและทำงานตาม API ที่กำหนดได้
* [x] ใข้งาน Docker เป็น ทำ Application เป็น Container, ใช้งาน Docker-compose ได้
* [x] กรณี success ต้อง response status code ให้เหมาะสม เช่น
* [x] 201 StatusCreated
* [x] 200 StatusOK
* [x] กรณี error ต่างๆ ให้ระบบ response status code ตามความเหมาะสม เช่น
* [x] 400 StatusBadRequest
* [x] 500 StatusInternalServerError
* [ ] มีการ Validate ข้อมูลที่รับมา เช่น จำนวนตัวอักษรที่รับได้
* [x] มีการป้องกันการใช้งาน API ของแอดมิน
* [x] มีการทำ Containerize ให้ใช้งานผ่าน Docker ได้
* [x] มี Test case เช่น Unit test ครอบคลุมส่วนของโค้ดและคะแนนจะขึ้นอยู่กับ Coverage ของ Test case ด้วย
* [x] โครงสร้างการทำงานถูกต้อง เช่น
* [x] ประกาศตัวแปรถูกต้องไหม
* [x] มี Test cases
* [x] การเชื่อมต่อระบบ Database
* [x] การทำ Transaction ถูกต้องเหมาะสม
* [x] ความสามารถในการบำรุงรักษา เช่น
* [x] การเขียนโค้ดง่ายต่อการอ่านและทำความเข้าใจ
* [x] การประมวลผลอยู่ใน Layer ที่เหมาะสม
* [x] ไม่มี If ซ้อนกันเยอะๆ
* [x] หลีกเลี่ยง Duplicate code ที่มากเกินไป
* [x] Method มีโค้ดที่เหมาะสมหรือทำงานสอดคล้องกัน
* [x] Method Parameter ที่รับมาต้องไม่มีจำนวนเยอะเกินไป
* [x] ประเภทตัวแปรเหมาะสม ตัวอย่าง เบอร์โทรศัพท์เก็บเป็น String
* [x] ป้องกันการเข้าถึงข้อมูล เช่น ดำเนินการกับข้อมูลได้เฉพาะที่ผู้ใช้นั้นๆ มีสิทธิ์
* [x] มีการจัดการ Clean up โค้ดอย่างเหมาะสม ลบส่วนที่ไม่ต้องการใช้งาน และมีการจัดรูปแบบโค้ด
* [x] การให้คำแนนจะแบ่งให้เป็นส่วน หากทำไม่เสร็จหรือไม่ได้ทำจะได้คะแนนตามส่วน