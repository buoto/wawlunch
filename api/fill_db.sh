#!/bin/bash
go run migrations/main.go fill_places
go run migrations/main.go fill_menus
go run migrations/main.go fill_dishes
