export interface CartTicket {
  id: string;
  itemType: string;
  eventName: string;
  stageName: string;
  performanceTime: string;
  section: string;
  row: string;
  seatNumber: number;
  price: string;
}

export interface Seat {
  id: string;
  seatStatus: string;
  performanceId: string;
  holdExpiry: string;
  seatId: number;
  row: string;
  seatNumber: number;
  section: string;
  uiIdentifier: string;
  price: string;
  stageId: number;
}
