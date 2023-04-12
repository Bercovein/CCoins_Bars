package com.ccoins.bars.service.impl;

import com.ccoins.bars.dto.BarHourDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.LongListDTO;
import com.ccoins.bars.exceptions.BadRequestException;
import com.ccoins.bars.model.BarHour;
import com.ccoins.bars.model.projection.IPBarHour;
import com.ccoins.bars.model.projection.IPDay;
import com.ccoins.bars.repository.IBarHoursRepository;
import com.ccoins.bars.repository.IDaysRepository;
import com.ccoins.bars.service.IDaysService;
import com.ccoins.bars.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ccoins.bars.exceptions.constant.ExceptionConstant.*;

@Service
public class DaysService implements IDaysService {

    private final IBarHoursRepository barHoursRepository;
    private final IDaysRepository daysRepository;


    @Autowired
    public DaysService(IBarHoursRepository barHoursRepository, IDaysRepository daysRepository) {
        this.barHoursRepository = barHoursRepository;
        this.daysRepository = daysRepository;
    }

    @Override
    public ResponseEntity<ListDTO> getDays(){

        List<IPDay> list = new ArrayList<>();

        try {
            list = this.daysRepository.findAllProjectedBy();
        }catch (Exception ignored){}

        return ResponseEntity.ok(ListDTO.builder().list(list).build());
    }

    @Override
    public ResponseEntity<ListDTO> getHoursByBar(Long barId){

        List<IPBarHour> list = new ArrayList<>();

        try {
            list = this.barHoursRepository.findAllProjectedByBarId(barId);
        }catch (Exception ignored){}

        return ResponseEntity.ok(ListDTO.builder().list(list).build());

    }

    @Override
    public List<BarHourDTO> saveOrUpdate(ListDTO request) {

        List<BarHour> list = new ArrayList<>();
        List<BarHourDTO> responseList = new ArrayList<>();
        request.getList().forEach( e -> MapperUtils.map(e, BarHour.class));

        try {
            list = this.barHoursRepository.saveAll(list);
            list.forEach(e -> responseList.add(BarHourDTO.convert(e)));

            return responseList;
        }catch(Exception e) {
            throw new BadRequestException(BAR_HOUR_SAVE_UPDATE_ERROR_CODE, BarHour.class, BAR_HOUR_SAVE_UPDATE_ERROR);
        }
    }

    @Override
    public void delete(LongListDTO request){

        try {
            this.barHoursRepository.deleteAllById(request.getList());
        }catch (Exception e){
            throw new BadRequestException(BAR_HOUR_DELETE_ERROR_CODE, BarHour.class, BAR_HOUR_DELETE_ERROR);
        }
    }
}
