# Employee Management System - REST API Documentation

This API follows the layered architecture, clean code practices, and proper exception handling. All response models are wrapped in a standard `ApiResponse` envelope.

## Base URL
`http://localhost:8080/api`

## Response Formats

### Standard Success Response Envelope
```json
{
  "message": "Success message description",
  "status": 200,
  "data": { ... } // Payload data (could be Object, Array, or absent)
}
```

### Standard Error Response Envelope
```json
{
  "message": "Error details or validation failures",
  "status": 400 // or 404, 500 etc.
}
```

---

## 1. Employee Module

### Add Employee
- **URL:** `/employees`
- **Method:** `POST`
- **Headers:** `Content-Type: application/json`
- **Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+12345678901",
  "department": "Engineering",
  "designation": "Software Engineer",
  "joiningDate": "2026-07-01",
  "isActive": true
}
```
- **Response (201 Created):**
```json
{
  "message": "Employee added successfully",
  "status": 201,
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+12345678901",
    "department": "Engineering",
    "designation": "Software Engineer",
    "joiningDate": "2026-07-01",
    "isActive": true
  }
}
```

### Get All Employees
- **URL:** `/employees`
- **Method:** `GET`
- **Response (200 OK):**
```json
{
  "message": "Employees retrieved successfully",
  "status": 200,
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "phone": "+12345678901",
      "department": "Engineering",
      "designation": "Software Engineer",
      "joiningDate": "2026-07-01",
      "isActive": true
    }
  ]
}
```

### Get Employee By ID
- **URL:** `/employees/{id}`
- **Method:** `GET`
- **Response (200 OK):**
```json
{
  "message": "Employee retrieved successfully",
  "status": 200,
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+12345678901",
    "department": "Engineering",
    "designation": "Software Engineer",
    "joiningDate": "2026-07-01",
    "isActive": true
  }
}
```
- **Response (404 Not Found):**
```json
{
  "message": "Employee not found with id: 1",
  "status": 404
}
```

### Update Employee
- **URL:** `/employees/{id}`
- **Method:** `PUT`
- **Headers:** `Content-Type: application/json`
- **Request Body:** Same as Add Employee
- **Response (200 OK):** Standard success response with updated details.

### Delete Employee
- **URL:** `/employees/{id}`
- **Method:** `DELETE`
- **Response (200 OK):**
```json
{
  "message": "Employee deleted successfully",
  "status": 200
}
```

### Search Employees by Name
- **URL:** `/employees/search/name`
- **Method:** `GET`
- **Query Parameters:** `name` (String)
- **Response (200 OK):** List of matching employees.

### Search Employees by Department
- **URL:** `/employees/search/department`
- **Method:** `GET`
- **Query Parameters:** `department` (String)
- **Response (200 OK):** List of matching employees.

---

## 2. Attendance Module

### Add Manual Attendance Record
- **URL:** `/attendance`
- **Method:** `POST`
- **Request Body:**
```json
{
  "employeeId": 1,
  "date": "2026-07-01",
  "checkIn": "09:00:00",
  "checkOut": "17:30:00",
  "status": "Present"
}
```
- **Response (201 Created):**
```json
{
  "message": "Attendance logged successfully",
  "status": 201,
  "data": {
    "attendanceId": 1,
    "employeeId": 1,
    "date": "2026-07-01",
    "checkIn": "09:00:00",
    "checkOut": "17:30:00",
    "status": "Present",
    "workingHours": "8 hours 30 mins"
  }
}
```

### Get All Attendance Records
- **URL:** `/attendance`
- **Method:** `GET`

### Get Attendance by Employee ID
- **URL:** `/attendance/employee/{employeeId}`
- **Method:** `GET`

### Check-In Employee (Live event today)
- **URL:** `/attendance/checkin/{employeeId}`
- **Method:** `POST`
- **Response (200 OK):** Logs check-in event at the current system time. If current time is after `09:15:00`, status is auto-determined as `Late`, otherwise `Present`.

### Check-Out Employee (Live event today)
- **URL:** `/attendance/checkout/{employeeId}`
- **Method:** `POST`
- **Response (200 OK):** Logs check-out event at current system time and calculates total working hours.

---

## 3. Leave Module

### Apply Leave Request
- **URL:** `/leaves`
- **Method:** `POST`
- **Request Body:**
```json
{
  "employeeId": 1,
  "leaveType": "Sick Leave",
  "fromDate": "2026-07-10",
  "toDate": "2026-07-12",
  "reason": "Medical checkup"
}
```
- **Response (201 Created):**
```json
{
  "message": "Leave applied successfully",
  "status": 201,
  "data": {
    "leaveId": 1,
    "employeeId": 1,
    "leaveType": "Sick Leave",
    "fromDate": "2026-07-10",
    "toDate": "2026-07-12",
    "reason": "Medical checkup",
    "status": "Pending"
  }
}
```

### Approve Leave
- **URL:** `/leaves/{id}/approve`
- **Method:** `PUT`
- **Response (200 OK):** Updates leave status to `Approved`.

### Reject Leave
- **URL:** `/leaves/{id}/reject`
- **Method:** `PUT`
- **Response (200 OK):** Updates leave status to `Rejected`.

### Get Leave History by Employee
- **URL:** `/leaves/employee/{employeeId}`
- **Method:** `GET`

---

## 4. Task Module

### Create & Assign Task
- **URL:** `/tasks`
- **Method:** `POST`
- **Request Body:**
```json
{
  "employeeId": 1,
  "title": "Build REST APIs",
  "description": "Create Spring Boot endpoints for Employee module",
  "priority": "High",
  "dueDate": "2026-07-05"
}
```
- **Response (201 Created):**
```json
{
  "message": "Task created successfully",
  "status": 201,
  "data": {
    "taskId": 1,
    "employeeId": 1,
    "title": "Build REST APIs",
    "description": "Create Spring Boot endpoints for Employee module",
    "priority": "High",
    "dueDate": "2026-07-05",
    "status": "Todo"
  }
}
```

### Get Tasks by Employee
- **URL:** `/tasks/employee/{employeeId}`
- **Method:** `GET`

### Update Task Status (Patch)
- **URL:** `/tasks/{id}/status`
- **Method:** `PATCH`
- **Query Parameters:** `status` (Allowed: `Todo`, `In Progress`, `Completed`)
- **Response (200 OK):** Updated task details.

### Get Pending Tasks
- **URL:** `/tasks/pending`
- **Method:** `GET`
- **Response (200 OK):** Returns all tasks across the system that do not have the status `Completed`.
