package place

import (
	"encoding/json"
	"net/http"

	"github.com/buoto/wawlunch/api/transport"
	"github.com/jinzhu/gorm"
)

func List(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var places []Place

		db.Model(&Place{}).Preload("Menus").Find(&places)

		json.NewEncoder(w).Encode(places)
	}
}

func Create(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var place Place

		err := json.NewDecoder(r.Body).Decode(&place)
		if err != nil {
			http.Error(w, transport.ErrorJSON("Error unmarshalling Place: %v", err), http.StatusBadRequest)
			return
		}

		db.Create(&place)

		json.NewEncoder(w).Encode(place)
	}
}
