# ระบบย่อยซื้อสลากกินแบ่งรัฐบาล
แอพพลิเคชั่นธนาคารต้องการเพิ่มฟีเจอร์ให้ผู้ใช้งานสามารถซื้อสลากกินแบ่งรัฐบาลได้ เพื่อตอบสนองกลุ่มนักเสี่ยงโชคยุคใหม่ที่ต้องการความสะดวก และไม่ต้องกังวลเรื่องการจัดเก็บสลากไว้กับตัวเอง รวมทั้งการขึ้นเงินก็สามารถทำผ่านแอพพลิเคชั่นได้อย่างง่ายดาย

## Business Requirements
- แอดมินสามารถเพิ่มข้อมูลสลากกินแบ่งเป็นตัวเลข 6 หลักได้
- ผู้ใช้สามารถแสดงรายการสลากกินแบ่งที่มีทั้งหมดได้
- ผู้ใช้สามารถซื้อสลากกินแบ่งได้
- ผู้ใช้สามารถแสดงรายการสลากกินแบ่งที่ตัวเองซื้อได้ พร้อมสรุปราคารวมที่ซื้อทั้งหมดได้
- ผู้ใช้สามารถลบสลากกินแบ่งที่ตัวเองซื้อได้
- ไม่ต้องคำนึงถึงเงินที่ผู้ใช้จะต้องจ่าย ระบบอื่นมีการตรวจสอบอยู่แล้ว UserID ของผู้ใช้เป็นตัวเลข 10 หลักเท่านั้น
- ฟีเจอร์ของแอดมินต้องมีการตรวจสอบสิทธิก่อนถึงจะใช้ได้
- ไม่ต้องกังวลเรื่องงวดของสลาก

## Prerequisites
* โปรเจคตั้งต้นคือ[โปรเจคนี้](https://github.com/KBTG-Kampus-ClassNest-SE-Java/assessment)
* กำหนดให้ส่งลิ้งค์คำตอบคือ github repository ที่เก็บโค้ดของคุณ `https://github.com/<your github name>/assessment`
* *จะต้อง* มีการใช้งาน PostgreSQL
* *จะต้อง* มีการใช้งานสร้าง Table ชื่อ `lottery` เพื่อใช้จัดการกับสลากกินแบ่งและ `user_ticket` เพื่อใช้เก็บรายการซื้อขายของผู้ใช้งาน
* *จะต้อง* เปิดใช้งานได้ผ่าน port `8888`
* *จะต้อง* เรียกใช้ Database URL ผ่านทาง Environment variable ชื่อ `DATABASE_URL` ได้


## User Stories
### Story: EXP01
	* As an admin, I want to add a new lottery ticket So that I can have a lottery store
	* ในฐานะผู้ดูแลระบบ ฉันต้องการเพิ่มใบสลากกินแบ่ง เพื่อที่จะสร้างคลังเก็บสลากกินแบ่ง
#### Technical Details: EXP01
* POST /admin/lotteries
* Request Body
```json
{
	"number": "123456",
	"price": 80,
	"amount": 1
}
```
* Response Body
```json
{
	"number": "123456"
}
```


### Story: EXP02
	* As a user, I want a list all lottery ticket So that I can pick what I want to buy
	* ในฐานะผู้ใช้ ฉันต้องการรายการ สลากทั้งหมด เพิื่อจะได้เลือกซื้อ
#### Technical Details: EXP02
* GET /lotteries
* Response Body
```json
{
	"numbers": ["000001","000002","123456"]
}
```

### Story: EXP03
	* As an admin, I want to buy a lottery ticket So that I can get a change to win
	* ในฐานะผู้ใช้ ฉันต้องการซื้อสลากกินแบ่ง เพื่อที่จะได้ลุ้นถูกหวย
#### Technical Details: EXP03
* POST /users/:userId/lotteries/:ticketId
* userId และ ticketId เป็นต่าที่ผู้ใช้ป้อนเข้ามา
* Response Body
```json
{
	"id": "1"
}
```
โดย id มาจาก ID ของตาราง `user_ticket`

### Story: EXP04
	* As an admin, I want to list all my lottery ticket So that I can see which one I have already bought and it cost
	* ในฐานะผู้ใช้ ฉันต้องการดูรายการสลากกินแบ่งทั้งหมดที่เคยซื้อ
#### Technical Details: EXP04
* POST /users/:userId/lotteries
* userId และ ticketId เป็นต่าที่ผู้ใช้ป้อนเข้ามา
* Response Body
```json
{
	"numbers": ["000001","000002","123456"],
	"count": 3,
	"cost": 240
}
```
