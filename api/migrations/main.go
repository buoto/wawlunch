package main

import (
	"log"
	"os"

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
		db.Create(&place.Place{
			Name:      "Aioli",
			Longitude: 21.011496,
			Latitude:  52.2360099,
		})
		db.Create(&place.Place{
			Name:      "Orzo",
			Longitude: 21.0152604,
			Latitude:  52.2223586,
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

	db = db.AutoMigrate(&place.Place{}, &place.Menu{})
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
