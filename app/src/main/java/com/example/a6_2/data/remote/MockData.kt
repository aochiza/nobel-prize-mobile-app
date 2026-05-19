package com.example.a6_2.data.remote

import com.example.a6_2.data.remote.dto.*

object MockData {

    fun getMockNobelPrizesResponse(): NobelPrizesResponse {
        return NobelPrizesResponse(
            nobelPrizes = listOf(
                // 2023 - Физика
                createMockPrize(
                    year = "2023",
                    category = "phy",
                    categoryName = "Physics",
                    laureates = listOf(
                        createMockLaureate(
                            id = "1",
                            name = "Pierre Agostini",
                            motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter",
                            birthDate = "1941-07-23",
                            country = "France",
                            city = "Paris"
                        ),
                        createMockLaureate(
                            id = "2",
                            name = "Ferenc Krausz",
                            motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter",
                            birthDate = "1962-05-17",
                            country = "Hungary",
                            city = "Székesfehérvár"
                        ),
                        createMockLaureate(
                            id = "3",
                            name = "Anne L'Huillier",
                            motivation = "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter",
                            birthDate = "1958-08-16",
                            country = "France",
                            city = "Paris"
                        )
                    )
                ),
                // 2023 - Химия
                createMockPrize(
                    year = "2023",
                    category = "che",
                    categoryName = "Chemistry",
                    laureates = listOf(
                        createMockLaureate(
                            id = "4",
                            name = "Moungi G. Bawendi",
                            motivation = "for the discovery and synthesis of quantum dots",
                            birthDate = "1961-03-15",
                            country = "France",
                            city = "Paris"
                        ),
                        createMockLaureate(
                            id = "5",
                            name = "Louis E. Brus",
                            motivation = "for the discovery and synthesis of quantum dots",
                            birthDate = "1943-08-10",
                            country = "USA",
                            city = "Cleveland"
                        ),
                        createMockLaureate(
                            id = "6",
                            name = "Alexei I. Ekimov",
                            motivation = "for the discovery and synthesis of quantum dots",
                            birthDate = "1945-02-28",
                            country = "Russia",
                            city = "St. Petersburg"
                        )
                    )
                ),
                // 2023 - Медицина
                createMockPrize(
                    year = "2023",
                    category = "med",
                    categoryName = "Physiology or Medicine",
                    laureates = listOf(
                        createMockLaureate(
                            id = "7",
                            name = "Katalin Karikó",
                            motivation = "for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines",
                            birthDate = "1955-01-17",
                            country = "Hungary",
                            city = "Szolnok"
                        ),
                        createMockLaureate(
                            id = "8",
                            name = "Drew Weissman",
                            motivation = "for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines",
                            birthDate = "1959-09-07",
                            country = "USA",
                            city = "Lexington"
                        )
                    )
                ),
                // 2022 - Физика
                createMockPrize(
                    year = "2022",
                    category = "phy",
                    categoryName = "Physics",
                    laureates = listOf(
                        createMockLaureate(
                            id = "9",
                            name = "Alain Aspect",
                            motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science",
                            birthDate = "1947-06-15",
                            country = "France",
                            city = "Agen"
                        ),
                        createMockLaureate(
                            id = "10",
                            name = "John F. Clauser",
                            motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science",
                            birthDate = "1942-12-01",
                            country = "USA",
                            city = "Pasadena"
                        ),
                        createMockLaureate(
                            id = "11",
                            name = "Anton Zeilinger",
                            motivation = "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science",
                            birthDate = "1945-05-20",
                            country = "Austria",
                            city = "Ried im Innkreis"
                        )
                    )
                ),
                // 2022 - Химия
                createMockPrize(
                    year = "2022",
                    category = "che",
                    categoryName = "Chemistry",
                    laureates = listOf(
                        createMockLaureate(
                            id = "12",
                            name = "Carolyn R. Bertozzi",
                            motivation = "for the development of click chemistry and bioorthogonal chemistry",
                            birthDate = "1966-10-10",
                            country = "USA",
                            city = "Boston"
                        ),
                        createMockLaureate(
                            id = "13",
                            name = "Morten Meldal",
                            motivation = "for the development of click chemistry and bioorthogonal chemistry",
                            birthDate = "1954-01-16",
                            country = "Denmark",
                            city = "Copenhagen"
                        ),
                        createMockLaureate(
                            id = "14",
                            name = "K. Barry Sharpless",
                            motivation = "for the development of click chemistry and bioorthogonal chemistry",
                            birthDate = "1941-04-28",
                            country = "USA",
                            city = "Philadelphia"
                        )
                    )
                ),
                // 2021 - Физика
                createMockPrize(
                    year = "2021",
                    category = "phy",
                    categoryName = "Physics",
                    laureates = listOf(
                        createMockLaureate(
                            id = "15",
                            name = "Syukuro Manabe",
                            motivation = "for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming",
                            birthDate = "1931-09-21",
                            country = "Japan",
                            city = "Shingu"
                        ),
                        createMockLaureate(
                            id = "16",
                            name = "Klaus Hasselmann",
                            motivation = "for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming",
                            birthDate = "1931-10-25",
                            country = "Germany",
                            city = "Hamburg"
                        ),
                        createMockLaureate(
                            id = "17",
                            name = "Giorgio Parisi",
                            motivation = "for the discovery of the interplay of disorder and fluctuations in physical systems from atomic to planetary scales",
                            birthDate = "1948-08-04",
                            country = "Italy",
                            city = "Rome"
                        )
                    )
                ),
                // 2021 - Медицина
                createMockPrize(
                    year = "2021",
                    category = "med",
                    categoryName = "Physiology or Medicine",
                    laureates = listOf(
                        createMockLaureate(
                            id = "18",
                            name = "David Julius",
                            motivation = "for the discovery of receptors for temperature and touch",
                            birthDate = "1955-11-04",
                            country = "USA",
                            city = "New York"
                        ),
                        createMockLaureate(
                            id = "19",
                            name = "Ardem Patapoutian",
                            motivation = "for the discovery of receptors for temperature and touch",
                            birthDate = "1967-10-02",
                            country = "Lebanon",
                            city = "Beirut"
                        )
                    )
                )
            ),
            meta = MetaDto(offset = 0, limit = 50, count = 7, total = 7)
        )
    }

    private fun createMockPrize(
        year: String,
        category: String,
        categoryName: String,
        laureates: List<LaureateDto>
    ): NobelPrizeDto {
        return NobelPrizeDto(
            awardYear = year,
            category = category,
            categoryFullName = FullNameDto(en = categoryName, se = null, no = null),
            dateAwarded = "$year-10-01",
            laureates = laureates
        )
    }

    private fun createMockLaureate(
        id: String,
        name: String,
        motivation: String,
        birthDate: String,
        country: String,
        city: String
    ): LaureateDto {
        return LaureateDto(
            id = id,
            fullName = FullNameDto(en = name, se = null, no = null),
            motivation = FullNameDto(en = motivation, se = null, no = null),
            birth = BirthDto(
                date = birthDate,
                place = PlaceDto(
                    city = FullNameDto(en = city, se = null, no = null),
                    country = FullNameDto(en = country, se = null, no = null)
                )
            ),
            nobelPrizes = null
        )
    }
}