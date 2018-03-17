package place

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/buoto/wawlunch/api/transport"
	"github.com/jinzhu/gorm"
)

func List(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var places []Place

		db.Model(&Place{}).Find(&places)

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

		if err = transport.DBErrors(w, db); err != nil {
			http.Error(w, transport.ErrorJSON(err.Error()), http.StatusBadRequest)
			return
		}

		json.NewEncoder(w).Encode(place)
	}
}

func ListMenus(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		params := r.URL.Query()

		var menus []Menu
		q := db.Model(&Menu{})
		if placeID, err := strconv.Atoi(params.Get("placeId")); err == nil {
			q = q.Where("place_id = ?", placeID)
		}

		q.Find(&menus)

		json.NewEncoder(w).Encode(menus)
	}
}
