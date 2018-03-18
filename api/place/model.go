package place

import (
	"encoding/json"
	"time"
)

type Place struct {
	ID        uint    `json:"id" gorm:"primary_key"`
	Name      string  `json:"name" gorm:"unique;not null"`
	Latitude  float64 `json:"latitude"`
	Longitude float64 `json:"longitude"`
	Street    string  `json:"street"`

	Menus []Menu `json:"-" gorm:"foreignkey:PlaceID"`
}

const DateFormat = "2006-01-02"

type Date time.Time

func (d *Date) MarshalJSON() ([]byte, error) {
	b := make([]byte, 0, len(DateFormat)+2)
	b = append(b, '"')
	b = (*time.Time)(d).AppendFormat(b, DateFormat)
	b = append(b, '"')

	return b, nil
}

func (d *Date) UnmarshalJSON(data []byte) error {
	// Ignore null, like in the main JSON package.
	if string(data) == "null" {
		return nil
	}

	// Fractional seconds are handled implicitly by Parse.
	t, err := time.Parse(`"`+DateFormat+`"`, string(data))
	*d = Date(t)
	return err
}

func Today() *Date {
	t := time.Now()
	return (*Date)(&t)
}

type Menu struct {
	ID      uint       `json:"id" gorm:"primary_key"`
	PlaceID uint       `json:"placeId" gorm:"index;not null"`
	Date    *time.Time `json:"date" gormw:"unique_index:place_id;not null"`
	Price   uint       `json:"price" gorm:"not null"`
	Dishes  []Dish     `json:"dishes" gorm:"foreignkey:MenuID"`
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

	err := json.Unmarshal(data, d)

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
	MenuID uint `json:"menuId" gorm:"index;not null"`

	Name   string   `json:"name" gorm:"unique;not null"`
	Type   DishType `json:"type" gorm:"size:4;not null"`
	IsVege bool     `json:"isVege" gorm:"not null"`

	ExtraPrice uint `json:"extraPrice,omitempty"`
}
