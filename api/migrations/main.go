package main

import (
	"log"
	"os"
	"time"

	"github.com/buoto/wawlunch/api/config"
	"github.com/buoto/wawlunch/api/place"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

var migrations = map[string]func(*gorm.DB){
	"remove_timestamps": func(db *gorm.DB) {
		m := db.Model(&place.Place{})
		m.DropColumn("created_at")
		m.DropColumn("deleted_at")
		m.DropColumn("updated_at")
	},
	"fill_places": func(db *gorm.DB) {
		t12, _ := time.Parse(place.ClockFormat, "12:00")
		t16, _ := time.Parse(place.ClockFormat, "16:00")

		db.Create(&place.Place{
			Name:        "Aioli",
			Longitude:   21.011496,
			Latitude:    52.2360099,
			Street:      "Świętokrzyska 18",
			Picture:     "http://buoto.me:8080/images/aioli_logo.jpg",
			CommonPrice: 2200,
			OpenFrom:    &t12,
			OpenTo:      &t16,
		})
		db.Create(&place.Place{
			Name:        "Orzo",
			Longitude:   21.0152604,
			Latitude:    52.2223586,
			Street:      "plac Konstytucji 5",
			Picture:     "http://buoto.me:8080/images/orzo_logo.png",
			CommonPrice: 2200,
			OpenFrom:    &t12,
			OpenTo:      &t16,
		})
	},
	"fill_menus": func(db *gorm.DB) {
		var places []place.Place
		db.Model(&place.Place{}).Find(&places)
		if len(places) < 2 {
			log.Fatal("Fill places first")
		}

		now := time.Now()
		tomorrow := now.Add(time.Hour * 24)

		db.Create(&place.Menu{
			PlaceID: places[0].ID,
			Date:    &now,
			Price:   2200,
		})
		db.Create(&place.Menu{
			PlaceID: places[0].ID,
			Date:    &tomorrow,
			Price:   2300,
		})

		db.Create(&place.Menu{
			PlaceID: places[1].ID,
			Date:    &now,
			Price:   2200,
		})
	},

	"fill_dishes": func(db *gorm.DB) {
		var menus []place.Menu
		db.Find(&menus)
		if len(menus) < 2 {
			log.Fatal("Fill menus first")
		}

		db.Create(&place.Dish{
			Name:   "Rosół",
			MenuID: menus[0].ID,
			Type:   place.Soup,
			IsVege: false,
		})
		db.Create(&place.Dish{
			Name:   "Kebab",
			MenuID: menus[0].ID,
			Type:   place.Main,
			IsVege: false,
		})
		db.Create(&place.Dish{
			Name:   "Roladki z bekonu",
			MenuID: menus[0].ID,
			Type:   place.Main,
			IsVege: false,
		})
		db.Create(&place.Dish{
			Name:   "Hummus falafel",
			MenuID: menus[0].ID,
			Type:   place.Main,
			IsVege: true,
		})
		db.Create(&place.Dish{
			Name:   "Lody waniliowe",
			MenuID: menus[0].ID,
			Type:   place.Dessert,
			IsVege: true,
		})
		db.Create(&place.Dish{
			Name:       "Kompot truskawkowy",
			MenuID:     menus[0].ID,
			Type:       place.Drink,
			IsVege:     true,
			ExtraPrice: 400,
		})
	},
}

func main() {
	conf, err := config.Load(config.ConfEnvName, "config/dev.json")
	if err != nil {
		log.Fatalf("Could not load config: %v", err)
	}

	db, err := gorm.Open(config.DBDriver, conf.DBConnectionString)
	if err != nil {
		log.Fatalf("Cannot connect to database: %v", err)
	}
	defer db.Close()

	log.Println("Running auto migration")

	db = db.AutoMigrate(&place.Place{}, &place.Menu{}, &place.Dish{})
	if errs := db.GetErrors(); len(errs) > 0 {
		for _, err := range errs {
			log.Println(err)
		}
	}

	if len(os.Args) == 2 {
		migrationKey := os.Args[1]

		if migration, ok := migrations[migrationKey]; ok {
			log.Println("Running migration", migrationKey)
			migration(db)
		} else {
			log.Fatal("Non existant migration key!")
		}
	}
}
