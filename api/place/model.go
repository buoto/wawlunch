package place

import "time"

type Place struct {
	ID        uint    `json:"id" gorm:"primary_key"`
	Name      string  `json:"name" gorm:"unique;not null"`
	Latitude  float64 `json:"latitude"`
	Longitude float64 `json:"longitude"`
	Street    string  `json:"street"`
	Menus     []Menu  `json:"-" gorm:"foreignkey:PlaceID"`
}

type Menu struct {
	ID      uint       `json:"id" gorm:"primary_key"`
	PlaceID uint       `json:"placeId" gorm:"index;not null"`
	Date    *time.Time `json:"date" gormw:"unique_index:place_id;not null"`
	Price   uint       `json:"price" gorm:"not null"`
	Dishes  []Dish     `json:"-" gorm:"foreignkey:MenuID"`
}

type DishType string

const (
	Soup    DishType = "soup"
	Main    DishType = "main"
	Dessert DishType = "dess"
	Drink   DishType = "drin"
)

type Dish struct {
	ID     uint `json:"id" gorm:"primary_key"`
	MenuID uint `json:"menuId" gorm:"index;not null"`

	Type DishType `json:"type" gorm:"size:4;not null"`

	IsExtra    bool `json:"isExtra"`
	ExtraPrice uint `json:"extraPrice"`
	// TODO: Dietary params
}
