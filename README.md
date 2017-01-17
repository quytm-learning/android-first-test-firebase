# first-test-firebase
Test Firebase


Firebase

# I. Features

## 1. Analytics
Chức năng trọng tâm của Firebase

## 2. Develop

- Authentication:g: gửi và nhận message thông qua nền tảng tin cậy
- Authentication: 
- Realtime Database
- Storage
- Test Lab: gửi web content nhanh hơn
- Test Lab:
- Crash Reporting: giữ cho app ổn định

## 3. Grow

- Notifications:
- Remote Config
- App Indexing
- Dynamic Links
- Invites:
AdWord

## 4. Earn
AdMob

## **5. Tính năng nổi bật**

### Realtime Database

- DB lưu dưới dạng JSON, được tự động cập nhật tới client
- Tự động tính toán quy mô ứng dụng và scale theo.
- Bảo mật lớp đầu, dựa trên kết nối an toàn SSL và chứng nhận 2048-bit
- Làm việc offline: sau khi ngoại tuyến một thời gian, client sẽ nhận mọi thay đổi của database

### Xác thực người dùng

- Firebase xây dựng sẵn phần Authen, và xác thực chỉ băng vài đoạn code. Ta không cần tốn thời gian cho backend

### Notification

- Gửi noti tới tất cả các Client

# II. Realtime Database 

## 1. Structure

- Kiểu dữ liệu lưu trữ: JSON
- Firebase cung cấp tối đa 32 level cho dữ liệu Json.

## Cần chú ý

- Database tất cả được lấy từ class: FirebaseDatabase.getInstance()
- Ta truy cập thông tin bên trong DB thông qua thằng DatabaseReference. Trong đó:
	+ có thể setValue cho child đó
	+ Bắt sự kiện khi có dữ liệu thay đổi: addValueEventListener
	+ Bắt sự kiện khi childrent thay đổi: addChildEventListener
	+ Bắt sự kiện thay đổi một lần: addListenerForSingleValueEvent (sử dụng trong trường hợp dữ liệu không cần cập nhật thường xuyên), nó chỉ bắt sự kiện một lần và sau đó dừng. Example, nhận profile của người dùng sau khi đăng nhập, sau đó ta không cần nhận thông tin profile sau đó nữa.
	+ Hủy bỏ sự kiện: removeEventListener
	+ Dữ liệu trả về trong các event là DataSnapshot, muốn lấy thông tin từ nó, ta sử dụng vòng for cho mảng dataSnapshot.getChildrent(): 
  
```
for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
    User user = dataSnapshot1.getValue(User.class);
    Log.i(TAG, user.toString());
}
```

- Dữ liệu add vào DB có kiểu:
	+ String
	+ Long
	+ Double
	+ Boolean
	+ Map<String, Object>
	+ List<Object>
	+ Class Object mà ta tự tạo, ví dụ:

```
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
```

- Ta thêm thông qua hàm ....child(name).setValue(data)
- Khi thêm dữ liệu, nếu key trùng với một key có sẵn thì nó sẽ bị ghi đè nếu ta sử dụng hàm setValue. Để tránh trường hợp bị ghi đè, ta sử dụng thêm push: ...push().setValue(...), khi đó, hệ thống sẽ tự tạo thêm key random để thêm vào ;
- Nếu sử dụng setValue cho key có sẵn, dữ liệu sẽ bị overwrite, để khắc phục, ta sử dụng hàm updateChildren(HashMap), và nó chỉ thêm các thuộc tính mới, còn thuộc tính cũ được giữ nguyên. Chú ý, tham số kiểu HashMap<k, v>  thì k có thể sử dụng theo kiểu đường dẫn, Eg: "/post/", "/post/details/", ...
- Xóa dữ liêu: removeValue, hoặc sử dụng giá trị null cho hàm setValue hoặc updateChildren.
- Lưu dữ liệu theo kiểu Transaction: bao gồm 2 giai đoạn: doTransaction và doComplete 
- Write data offline: nếu ta thay đổi dữ liệu trong trạng thái không có Internet thì hệ thống lưu lại trạng thái đó, khi có mạng sẽ cập nhật lại dữ liệu (nếu activity bị chết sau đó, thì nó vẫn cập nhật được nhưng có độ trễ mất khoảng 10s).
	+ Client xóa item offline, server sửa dữ liệu item -> Khi có mạng, client nhận dữ liệu sửa đổi của server, item không bị xóa, chỉ bị thay đổi dữ liệu.

## 2. Query dữ liệu

- Sử dụng class Query
- Order: DatabaseReference.child().orderByChild, hoặc orderByKey, orderByValue.
- Filter: limitToFirst, limitToLast, ... 

## 3. Khả năng lưu trữ offline

- DB lưu trữ offline, có thể truy cập.

## 4. Tự động backup

- Chỉ sử dụng trong bản trả phí.


# III. Messaging

# IV. Pricing

|a

