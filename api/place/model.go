package place

type Place struct {
	ID        uint    `gorm:"primary_key" json:"id"`
	Name      string  `json:"name" gorm:"unique;not null"`
	Latitude  float64 `json:"latitude"`
	Longitude float64 `json:"longitude"`
	Menus     []Menu  `gorm:"foreignkey:PlaceID" json:"-"`
}

type Menu struct {
	ID      uint `gorm:"primary_key" json:"id"`
	PlaceID uint `json:"placeId"`
}
