package main

import (
	"log"
	"os"

	"github.com/buoto/wawlunch/api/config"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

var migrations = map[string]func(*gorm.DB){}

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

	if len(os.Args) == 1 {
		log.Println("Running auto migration")

		db.AutoMigrate(&place.Place{})
	} else {
		migrationKey := os.Args[1]

		if migration, ok := migrations[migrationKey]; ok {
			log.Println("Running migration", migrationKey)
			migration(db)
		} else {
			log.Fatal("Non existant migration key!")
		}
	}
}
