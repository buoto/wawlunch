package place

import "github.com/jinzhu/gorm"

type Place struct {
	gorm.Model
	Name      string  `json:"name"`
	Latitude  float64 `json:"latitude"`
	Longitude float64 `json:"longitude"`
}
