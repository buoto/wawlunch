package place

import (
	"encoding/json"
	"time"
)

type Place struct {
	ID          uint    `json:"id" gorm:"primary_key"`
	Name        string  `json:"name" gorm:"unique;not null"`
	Latitude    float64 `json:"latitude"`
	Longitude   float64 `json:"longitude"`
	Street      string  `json:"street"`
	Picture     string  `json:"picture"`
	InsidePhoto string  `json:"insidePhoto"`

	OpenFrom *time.Time `json:"openFrom"`
	OpenTo   *time.Time `json:"openTo"`

	CommonPrice uint `json:"commonPrice"`

	Menus []Menu `json:"-"`
}

func (p *Place) UnmarshalJSON(data []byte) error {
	type placeJSON Place
	d := struct {
		*placeJSON
		OpenFrom *ClockTime `json:"openFrom"`
		OpenTo   *ClockTime `json:"openTo"`
	}{placeJSON: (*placeJSON)(p)}

	err := json.Unmarshal(data, &d)

	p.OpenFrom = (*time.Time)(d.OpenFrom)
	p.OpenTo = (*time.Time)(d.OpenTo)
	return err
}

func (p *Place) MarshalJSON() ([]byte, error) {
	type placeJSON Place
	d := struct {
		*placeJSON
		OpenFrom *ClockTime `json:"openFrom"`
		OpenTo   *ClockTime `json:"openTo"`
	}{(*placeJSON)(p), (*ClockTime)(p.OpenFrom), (*ClockTime)(p.OpenTo)}

	return json.Marshal(d)
}

type Menu struct {
	ID      uint       `json:"id" gorm:"primary_key"`
	PlaceID uint       `json:"placeId" gorm:"unique_index:unique_date_place;not null"`
	Date    *time.Time `json:"date" gorm:"unique_index:unique_date_place;not null"`
	Price   uint       `json:"price" gorm:"not null"`
	Dishes  []Dish     `json:"dishes"`
}

func (m *Menu) MarshalJSON() ([]byte, error) {
	type menuJSON Menu
	d := struct {
		*menuJSON
		Date *Date `json:"date"`
	}{(*menuJSON)(m), (*Date)(m.Date)}

	return json.Marshal(d)
}

func (m *Menu) UnmarshalJSON(data []byte) error {
	type menuJSON Menu
	d := struct {
		*menuJSON
		Date *Date `json:"date"`
	}{menuJSON: (*menuJSON)(m)}

	err := json.Unmarshal(data, &d)

	m.Date = (*time.Time)(d.Date)
	return err
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
	MenuID uint `json:"menuId" gorm:"unique_index:unique_name_menu;not null"`

	Name   string   `json:"name" gorm:"unique_index:unique_name_menu;not null"`
	Type   DishType `json:"type" gorm:"size:4;not null"`
	IsVege bool     `json:"isVege" gorm:"not null;default:false"`

	ExtraPrice uint `json:"extraPrice,omitempty"`
}
