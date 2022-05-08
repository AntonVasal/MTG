package com.example.mtg.core.listsSorters;

import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.models.countModels.MultiResultsModel;
import com.example.mtg.models.countModels.SubResultsModel;

import java.util.ArrayList;

public class MainListsSorter {
    private ArrayList<AddResultsModel> addList;
    private ArrayList<SubResultsModel> subList;
    private ArrayList<DivResultsModel> divList;
    private ArrayList<MultiResultsModel> multiList;
    private ArrayList<AddResultsModel> addListToReturn;
    private ArrayList<SubResultsModel> subListToReturn;
    private ArrayList<DivResultsModel> divListToReturn;
    private ArrayList<MultiResultsModel> multiListToReturn;

    public MainListsSorter() {
    }

    public void setAddList(ArrayList<AddResultsModel> addList) {
        this.addList = addList;
    }

    public void setSubList(ArrayList<SubResultsModel> subList) {
        this.subList = subList;
    }

    public void setDivList(ArrayList<DivResultsModel> divList) {
        this.divList = divList;
    }

    public void setMultiList(ArrayList<MultiResultsModel> multiList) {
        this.multiList = multiList;
    }

    public ArrayList<AddResultsModel> sortAddNaturalModels() {
        addList.sort((addResultsModel, t1) -> t1.getAddNaturalScore() - addResultsModel.getAddNaturalScore());
        addListToReturn = new ArrayList<>(addList);
        addListToReturn.removeIf(addResultsModel -> addResultsModel.getAddNaturalScore() == 0);
        return addListToReturn;
    }

    public ArrayList<AddResultsModel> sortAddIntegerModels() {
        addList.sort((addResultsModel, t1) -> t1.getAddIntegerScore() - addResultsModel.getAddIntegerScore());
        addListToReturn = new ArrayList<>(addList);
        addListToReturn.removeIf(addResultsModel -> addResultsModel.getAddIntegerScore() == 0);
        return addListToReturn;
    }

    public ArrayList<AddResultsModel> sortAddDecimalModels() {
        addList.sort((addResultsModel, t1) -> t1.getAddDecimalScore() - addResultsModel.getAddDecimalScore());
        addListToReturn = new ArrayList<>(addList);
        addListToReturn.removeIf(addResultsModel -> addResultsModel.getAddDecimalScore() == 0);
        return addListToReturn;
    }

    public ArrayList<DivResultsModel> sortDivNaturalsModels() {
        divList.sort((divResultsModel, t1) -> t1.getDivNaturalScore() - divResultsModel.getDivNaturalScore());
        divListToReturn = new ArrayList<>(divList);
        divListToReturn.removeIf(divResultsModel -> divResultsModel.getDivNaturalScore() == 0);
        return divListToReturn;
    }

    public ArrayList<DivResultsModel> sortDivIntegersModels() {
        divList.sort((divResultsModel, t1) -> t1.getDivIntegerScore() - divResultsModel.getDivIntegerScore());
        divListToReturn = new ArrayList<>(divList);
        divListToReturn.removeIf(divResultsModel -> divResultsModel.getDivIntegerScore() == 0);
        return divListToReturn;
    }

    public ArrayList<DivResultsModel> sortDivDecimalsModels() {
        divList.sort((divResultsModel, t1) -> t1.getDivDecimalScore() - divResultsModel.getDivDecimalScore());
        divListToReturn = new ArrayList<>(divList);
        divListToReturn.removeIf(divResultsModel -> divResultsModel.getDivDecimalScore() == 0);
        return divListToReturn;
    }

    public ArrayList<MultiResultsModel> sortMultiNaturalsModels() {
        multiList.sort((multiResultsModel, t1) -> t1.getMultiNaturalScore() - multiResultsModel.getMultiNaturalScore());
        multiListToReturn = new ArrayList<>(multiList);
        multiListToReturn.removeIf(multiResultsModel -> multiResultsModel.getMultiNaturalScore() == 0);
        return multiListToReturn;
    }

    public ArrayList<MultiResultsModel> sortMultiIntegersModels() {
        multiList.sort((multiResultsModel, t1) -> t1.getMultiIntegerScore() - multiResultsModel.getMultiIntegerScore());
        multiListToReturn = new ArrayList<>(multiList);
        multiListToReturn.removeIf(multiResultsModel -> multiResultsModel.getMultiIntegerScore() == 0);
        return multiListToReturn;
    }

    public ArrayList<MultiResultsModel> sortMultiDecimalsModels() {
        multiList.sort((multiResultsModel, t1) -> t1.getMultiDecimalScore() - multiResultsModel.getMultiDecimalScore());
        multiListToReturn = new ArrayList<>(multiList);
        multiListToReturn.removeIf(multiResultsModel -> multiResultsModel.getMultiDecimalScore() == 0);
        return multiListToReturn;
    }

    public ArrayList<SubResultsModel> sortSubNaturalsModels() {
        subList.sort((subResultsModel, t1) -> t1.getSubNaturalScore() - subResultsModel.getSubNaturalScore());
        subListToReturn = new ArrayList<>(subList);
        subListToReturn.removeIf(subResultsModel -> subResultsModel.getSubNaturalScore() == 0);
        return subListToReturn;
    }

    public ArrayList<SubResultsModel> sortSubIntegersModels() {
        subList.sort((subResultsModel, t1) -> t1.getSubIntegerScore() - subResultsModel.getSubIntegerScore());
        subListToReturn = new ArrayList<>(subList);
        subListToReturn.removeIf(subResultsModel -> subResultsModel.getSubIntegerScore() == 0);
        return subListToReturn;
    }

    public ArrayList<SubResultsModel> sortSubDecimalsModels() {
        subList.sort((subResultsModel, t1) -> t1.getSubDecimalScore() - subResultsModel.getSubDecimalScore());
        subListToReturn = new ArrayList<>(subList);
        subListToReturn.removeIf(subResultsModel -> subResultsModel.getSubDecimalScore() == 0);
        return subListToReturn;
    }


}
