# EmployeeManagement

postman
=======
{
	"info": {
		"_postman_id": "20d29a5b-e1c7-47c8-9b68-4316af705b63",
		"name": "Employee Management",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "Company",
			"item": [
				{
					"name": "Create Company",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\":\"TCS\",\n    \"details\":\"this is an organisation\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "{{baseUrl}}/company",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"company"
							]
						}
					},
					"response": []
				},
				{
					"name": "Update Company",
					"request": {
						"method": "PUT",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/company/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"company",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": ""
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get Company By Id",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/company/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"company",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": "3"
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get List of Company",
					"request": {
						"method": "POST",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/company/getAll",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"company",
								"getAll"
							]
						}
					},
					"response": []
				},
				{
					"name": "Delete Company",
					"request": {
						"method": "DELETE",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/company/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"company",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": ""
								}
							]
						}
					},
					"response": []
				}
			]
		},
		{
			"name": "Department",
			"item": [
				{
					"name": "Create Department",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\":\"python\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "{{baseUrl}}/department",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"department"
							]
						}
					},
					"response": []
				},
				{
					"name": "Update Department",
					"request": {
						"method": "PUT",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/department/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"department",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": ""
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get Department By Id",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/department/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"department",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": "1"
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get List of Department",
					"request": {
						"method": "POST",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/department/getAll",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"department",
								"getAll"
							]
						}
					},
					"response": []
				},
				{
					"name": "Delete Department",
					"request": {
						"method": "DELETE",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/department:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"department:id"
							]
						}
					},
					"response": []
				}
			]
		},
		{
			"name": "Role",
			"item": [
				{
					"name": "Create Role",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\":\"USER\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "{{baseUrl}}/role",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"role"
							]
						}
					},
					"response": []
				},
				{
					"name": "Update Role",
					"request": {
						"method": "PUT",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\":\"ADMIN\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "{{baseUrl}}/role/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"role",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": "1"
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get Role By Id",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/role/:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"role",
								":id"
							],
							"variable": [
								{
									"key": "id",
									"value": "1"
								}
							]
						}
					},
					"response": []
				},
				{
					"name": "Get List of Role",
					"request": {
						"method": "POST",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/role/getAll",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"role",
								"getAll"
							]
						}
					},
					"response": []
				},
				{
					"name": "Delete Role",
					"request": {
						"method": "DELETE",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/role:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"role:id"
							]
						}
					},
					"response": []
				}
			]
		},
		{
			"name": "Employee",
			"item": [
				{
					"name": "Create Employee",
					"request": {
						"method": "POST",
						"header": [],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"firstName\":\"rupesh\",\n    \"lastName\":\"kumar\",\n    \"\"\n}",
							"options": {
								"raw": {
									"language": "json"
								}
							}
						},
						"url": {
							"raw": "{{baseUrl}}/employee",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"employee"
							]
						}
					},
					"response": []
				},
				{
					"name": "Update Employee",
					"request": {
						"method": "PUT",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/employee:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"employee:id"
							]
						}
					},
					"response": []
				},
				{
					"name": "Get Employee By Id",
					"request": {
						"method": "GET",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/employee:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"employee:id"
							]
						}
					},
					"response": []
				},
				{
					"name": "Get List of Employee",
					"request": {
						"method": "POST",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/employee/getAll",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"employee",
								"getAll"
							]
						}
					},
					"response": []
				},
				{
					"name": "Delete Employee",
					"request": {
						"method": "DELETE",
						"header": [],
						"url": {
							"raw": "{{baseUrl}}/employee:id",
							"host": [
								"{{baseUrl}}"
							],
							"path": [
								"employee:id"
							]
						}
					},
					"response": []
				}
			]
		}
	]
}
