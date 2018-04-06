package rolling.pandas.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rolling.pandas.server.dao.PlaceRepository
import rolling.pandas.server.domain.Place
import rolling.pandas.server.loggerFor

@RestController
@RequestMapping("/place")
class PlaceController(private val placeRepository: PlaceRepository) {

    private val log = loggerFor(javaClass)

    @GetMapping
    fun get(@RequestParam(required = false) type: String?, @RequestParam(required = false) name: String?): List<Place> {
        log.info("called with type=$type and name=$name")
        return if (name != null && type != null)
            placeRepository.findByTypeAndName(type, name)
        else if (type != null)
            placeRepository.findByType(type)
        else if (name != null)
            placeRepository.findByNameLike(name)
        else placeRepository.findAll()
    }
}