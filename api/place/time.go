package place

import (
	"time"
)

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

const ClockFormat = "15:04"

type ClockTime time.Time

func (d *ClockTime) MarshalJSON() ([]byte, error) {
	b := make([]byte, 0, len(ClockFormat)+2)
	b = append(b, '"')
	b = (*time.Time)(d).UTC().AppendFormat(b, ClockFormat)
	b = append(b, '"')

	return b, nil
}

func (d *ClockTime) UnmarshalJSON(data []byte) error {
	// Ignore null, like in the main JSON package.
	if string(data) == "null" {
		return nil
	}

	// Fractional seconds are handled implicitly by Parse.
	t, err := time.Parse(`"`+ClockFormat+`"`, string(data))
	*d = ClockTime(t)
	return err
}
