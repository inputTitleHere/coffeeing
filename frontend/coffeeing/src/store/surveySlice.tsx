import {createSlice,PayloadAction} from '@reduxjs/toolkit'
import { SurveyType } from 'types/StoreTypes';


const initialState:SurveyType = {
  currentPage:1,
  totalPage:0,
  roasting:0,
  acid:0,
  body:0,
  userId:0,
  machine:0,
  // 원두 or 캡슐 type
  type:0,
}

const surveySlice = createSlice({
  name:'surveySlice',
  initialState,
  reducers:{
    addCurrentPage(state){
      state.currentPage += 1
      console.log('current page', state.currentPage)
    },
    saveRoasting(state, action:PayloadAction<number>){
      state.roasting = action.payload
      console.log('roasting',state.roasting)
    },
  },
})

export default surveySlice.reducer;
export const {addCurrentPage,saveRoasting} = surveySlice.actions;