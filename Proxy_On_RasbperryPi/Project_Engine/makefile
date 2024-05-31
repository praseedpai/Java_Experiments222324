all:
	g++ -c -std=c++17  Utils.cpp  -o Utils.o
	g++ -c -std=c++17  ServerSocket.cpp -o ServerSocket.o
	g++ -c -std=c++17  ClientSocket.cpp -o ClientSocket.o 
	g++ -c -std=c++17  CPingClient.cpp  -o CPingClient.o
	g++ -c -std=c++17  CProtocolServer.cpp -o CProtocolServer.o
	g++ -std=c++17 spawner.cpp Utils.o  ServerSocket.o ClientSocket.o  CProtocolServer.o  -lpthread -o spawner.exe
	g++ -std=c++17 first.cpp ServerSocket.o ClientSocket.o CPingClient.o CProtocolServer.o   -lpthread -o first.exe

	